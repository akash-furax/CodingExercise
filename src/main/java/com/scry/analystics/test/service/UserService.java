package com.scry.analystics.test.service;

import com.scry.analystics.test.entity.Shop;
import com.scry.analystics.test.repository.ShopRepository;
import com.scry.analystics.test.utils.DistanceUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class UserService {

    private final ShopRepository shopRepository;

    private final GeocodingService geocodingService;

    public UserService(ShopRepository shopRepository, GeocodingService geocodingService) {
        this.shopRepository = shopRepository;
        this.geocodingService = geocodingService;
    }

    public Shop findNearestLocationForUser(Double lat, Double lon) {
        List<Shop> allShops = shopRepository.findAll();

        //Local optimization to minimize remote calls.
        // If we do not want calculation based shortlisting, comment below line.
        allShops = DistanceUtils.findNearestLocations(lat, lon, allShops);

        //Calling google distance matrix API to find the nearest locations.
        // parallelize calls since they are independent.
        List<CompletableFuture<Map.Entry<Double, Shop>>> futures = new ArrayList<>();
        for (Shop shop : allShops) {
            CompletableFuture<Map.Entry<Double, Shop>> future = CompletableFuture.
                    supplyAsync(() -> Map.entry(geocodingService.calculateDistanceBetweenLatLong(lat,
                            shop.getShopAddress().getLocation().getLatitude(),
                            lon,
                            shop.getShopAddress().getLocation().getLongitude()), shop)
                    );
            futures.add(future);
        }


        //TODO  Stream specific but too cluttered. Will try to improve code.
//        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
//                // avoid throwing an exception in the join() call
//                .exceptionally(ex -> null);
//        allFutures.thenApply(v -> {
//            return futures.stream().map(future -> future.join())
//                    .min(Comparator.comparing(Map.Entry::getKey));
//        }).get().get().getValue();


        // get min distance entry based on key and return data.
        try {
            return Collections.min(futures, new Comparator<Future<Map.Entry<Double, Shop>>>() {
                @Override
                public int compare(Future<Map.Entry<Double, Shop>> optional1, Future<Map.Entry<Double, Shop>> optional2) {
                    try {
                        Map.Entry<Double, Shop> o1 = optional1.get();
                        Map.Entry<Double, Shop> o2 = optional2.get();
                        return o1.getKey().compareTo(o2.getKey());
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException("Error comparing distance. Please verify.", e);
                    }
                }
            }).get().getValue();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error comparing min distance. Please verify.", e);
        }
    }
}
