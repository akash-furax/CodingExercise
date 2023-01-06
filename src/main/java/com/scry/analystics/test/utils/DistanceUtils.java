package com.scry.analystics.test.utils;

import com.scry.analystics.test.entity.Shop;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DistanceUtils {


    public static List<Shop> findNearestLocations(Double lat, Double lon, List<Shop> allShops) {
        TreeMap<Double, Shop> orderedMap = new TreeMap<>();
        allShops.forEach(shop -> orderedMap.put(distance(lat, lon,
                shop.getShopAddress().getLocation().getLatitude(),
                shop.getShopAddress().getLocation().getLongitude()), shop));
        return orderedMap.entrySet().stream().limit(5).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
