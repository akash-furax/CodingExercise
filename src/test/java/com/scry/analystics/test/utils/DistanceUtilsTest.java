package com.scry.analystics.test.utils;

import com.scry.analystics.test.entity.Shop;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DistanceUtilsTest {
    /**
     * Method under test: {@link DistanceUtils#findNearestLocations(Double, Double, List)}
     */
    @Test
    void testFindNearestLocations() {
        assertTrue(DistanceUtils.findNearestLocations(10.0d, 10.0d, new ArrayList<>()).isEmpty());
    }

    /**
     * Method under test: {@link DistanceUtils#findNearestLocations(Double, Double, List)}
     */
    @Test
    void testFindNearestLocations2() {
        Shop shop1 = TestUtil.createShop("10");
        Shop shop2 = TestUtil.createShop("20");
        Shop shop3 = TestUtil.createShop("30");
        Shop shop4 = TestUtil.createShop("40");
        Shop shop5 = TestUtil.createShop("50");
        Shop shop6 = TestUtil.createShop("60");
        Shop shop7 = TestUtil.createShop("70");
        Shop shop8 = TestUtil.createShop("80");
        Shop shop9 = TestUtil.createShop("90");
        Shop shop10 = TestUtil.createShop("99");
        ArrayList<Shop> shopList = new ArrayList<>();
        shopList.add(shop1);
        shopList.add(shop2);
        shopList.add(shop3);
        shopList.add(shop4);
        shopList.add(shop5);
        shopList.add(shop6);
        shopList.add(shop7);
        shopList.add(shop8);
        shopList.add(shop9);
        shopList.add(shop10);
        assertEquals(5, DistanceUtils.findNearestLocations(9.0d, 9.0d, shopList).size());
    }
}

