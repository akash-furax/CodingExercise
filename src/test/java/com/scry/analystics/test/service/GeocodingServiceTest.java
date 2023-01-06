package com.scry.analystics.test.service;

import com.scry.analystics.test.model.LocationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {GeocodingService.class})
@ExtendWith(MockitoExtension.class)
class GeocodingServiceTest {
    @MockBean
    final WebClient mock = mock(WebClient.class);
    WebClient.RequestHeadersUriSpec uriSpecMock;
    WebClient.RequestHeadersSpec headersSpecMock;
    WebClient.ResponseSpec responseSpecMock;
    @InjectMocks
    private GeocodingService geocodingService;

    @BeforeEach()
    public void setup() {
        uriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        headersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        responseSpecMock = mock(WebClient.ResponseSpec.class);
        when(mock.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(ArgumentMatchers.<String>notNull())).thenReturn(headersSpecMock);
        when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
    }


    /**
     * Method under test: {@link GeocodingService#retrieveLatLongForAddress(String)}
     */
    @Test
    void testRetrieveLatLongForAddress() {

        when(responseSpecMock.bodyToMono(ArgumentMatchers.<Class<String>>notNull()))
                .thenReturn(Mono.just("{\"results\":[{\"geometry\":{\"location\":{\"lat\":123,\"lng\":1234}}}]}"));

        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setGroupingUsed(false);
        ReflectionTestUtils.setField(geocodingService, "df", decimalFormat);
        LocationDTO locationDTO = geocodingService.retrieveLatLongForAddress("42 Main St");
        assertEquals(locationDTO.getLongitude(), 1234D);
        assertEquals(locationDTO.getLatitude(), 123D);
    }

    /**
     * Method under test: {@link GeocodingService#calculateDistanceBetweenLatLong(Double, Double, Double, Double)}
     */
    @Test
    void testCalculateDistanceBetweenLatLong() {
        when(responseSpecMock.bodyToMono(ArgumentMatchers.<Class<String>>notNull()))
                .thenReturn(Mono.just("123.12"));

        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setGroupingUsed(false);
        ReflectionTestUtils.setField(geocodingService, "df", decimalFormat);

        Double distance = geocodingService.calculateDistanceBetweenLatLong(10.0d, 10.0d, 10.0d, 10.0d);
        assertEquals(123.12D, distance);
    }
}

