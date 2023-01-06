package com.scry.analystics.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scry.analystics.test.model.LocationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.Objects;

@Service
@Slf4j
public class GeocodingService {

    private static final String GET_COORDINATES_URL = "/maps/api/geocode/json";

    private static final String GET_DISTANCE_BETWEEN_COORDINATES = "/maps/api/distancematrix/json";

    private final WebClient webclient;

    private final DecimalFormat df;

    private final String gcpMapsApiKey;

    public GeocodingService(@Autowired WebClient webclient, @Value("${application.gcp.maps.apiKey}") String gcpMapsApiKey) {
        this.webclient = webclient;
        this.gcpMapsApiKey = gcpMapsApiKey;
        df = new DecimalFormat();
        df.setGroupingUsed(false);
    }

    private static Double parseDistance(String responseBody) {
        JsonNode rootNode = null;
        try {
            rootNode = new ObjectMapper().readTree(responseBody);
            JsonNode resultsNode = rootNode.path("rows");
            if (resultsNode.has(0)) {
                JsonNode distanceNode = resultsNode.get(0).path("elements").get(0).path("distance");
                double distance = distanceNode.get("value").asDouble();
                return distance;
            }
            throw new RuntimeException("No Distance Data available for this location");
        } catch (JsonProcessingException e) {
            log.error("Error retrieving distance from google geocoding API for response: [{}]", responseBody);
        }
        return Double.MAX_VALUE;
    }

    private static LocationDTO parseLocation(String responseBody) {
        JsonNode rootNode = null;
        try {
            rootNode = new ObjectMapper().readTree(responseBody);
            JsonNode resultsNode = rootNode.path("results");
            if (resultsNode.has(0)) {
                JsonNode geometryNode = resultsNode.get(0).path("geometry");
                JsonNode locationNode = geometryNode.path("location");
                double lat = locationNode.get("lat").asDouble();
                double lon = locationNode.get("lng").asDouble();
                LocationDTO locationDTO = new LocationDTO();
                locationDTO.setLatitude(lat);
                locationDTO.setLongitude(lon);
                return locationDTO;
            }
            throw new RuntimeException("No Data available for this location");
        } catch (JsonProcessingException e) {
            log.error("Error retrieving location from google geocoding API for response: [{}]", responseBody);
        }
        return null;
    }

    public LocationDTO retrieveLatLongForAddress(String address) {
        Mono<LocationDTO> locationMono = webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_COORDINATES_URL)
                        .queryParam("key", gcpMapsApiKey)
                        .queryParam("address", address)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(GeocodingService::parseLocation);
        return Objects.requireNonNull(locationMono.block(), () -> "Could not parse response from Google Geocoding API");
    }

    public Double calculateDistanceBetweenLatLong(Double lat1, Double lat2, Double lng1, Double lng2) {
        Mono<Double> locationMono = webclient.get()
                .uri(uriBuilder -> uriBuilder.path(GET_DISTANCE_BETWEEN_COORDINATES)
                        .queryParam("key", gcpMapsApiKey)
                        .queryParam("origins", String.format("%s,%s", df.format(lat1), df.format(lng1)))
                        .queryParam("destinations", String.format("%s,%s", df.format(lat2), df.format(lng2)))
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(GeocodingService::parseDistance);
        return locationMono.block();
    }
}