package com.scry.analystics.test.utils;

import com.scry.analystics.test.entity.Shop;
import com.scry.analystics.test.model.ShopDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectMapperUtilsTest {
    /**
     * Method under test: {@link ObjectMapperUtils#map(Object, Class)}
     */
    @Test
    void testMap() {
        assertEquals("Entity", ObjectMapperUtils.map("Entity", Object.class));
        assertEquals("42", ObjectMapperUtils.map("42", Object.class));
        assertEquals("Destination", ObjectMapperUtils.map("Source", "Destination"));
        assertEquals("Destination", ObjectMapperUtils.map(42, "Destination"));
        assertEquals("Destination", ObjectMapperUtils.map(1, "Destination"));
    }

    /**
     * Method under test: {@link ObjectMapperUtils#map(Object, Object)}
     */
    @Test
    void testMap3() {
        ObjectMapperUtils.map("42", Integer.class);
    }

    /**
     * Method under test: {@link ObjectMapperUtils#mapAll(Collection, Class)}
     */
    @Test
    void testMapAll() {
        ArrayList<Object> entityList = new ArrayList<>();
        assertTrue(ObjectMapperUtils.mapAll(entityList, Object.class).isEmpty());
    }

    /**
     * Method under test: {@link ObjectMapperUtils#mapAll(Collection, Class)}
     */
    @Test
    void testMapAll2() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("42");
        assertEquals(1, ObjectMapperUtils.mapAll(objectList, Object.class).size());
    }

    /**
     * Method under test: {@link ObjectMapperUtils#mapAll(Collection, Class)}
     */
    @Test
    void testMapAll3() {
        ArrayList<Shop> objectList = new ArrayList<>();
        objectList.add(TestUtil.createShop("123"));
        objectList.add(TestUtil.createShop("1234"));
        assertEquals(2, ObjectMapperUtils.mapAll(objectList, ShopDTO.class).size());
    }

    /**
     * Method under test: {@link ObjectMapperUtils#mapAll(Collection, Class)}
     */
    @Test
    void testMapAll4() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add(new ArrayList<>());
        assertEquals(1, ObjectMapperUtils.mapAll(objectList, Object.class).size());
    }

    /**
     * Method under test: {@link ObjectMapperUtils#mapAll(Collection, Class)}
     */
    @Test
    void testMap2() {
        Shop shop = TestUtil.createShop("123");
        ShopDTO shopDTO = ObjectMapperUtils.map(shop, ShopDTO.class);
        assertEquals(shopDTO.getShopName(), shop.getShopName());
        assertEquals(shopDTO.getShopAddress().getPostCode(), shop.getShopAddress().getPostCode());
        assertEquals(shopDTO.getShopAddress().getPostCode(), shop.getShopAddress().getPostCode());
        assertEquals(shopDTO.getShopAddress().getNumber(), shop.getShopAddress().getNumber());
        assertEquals(shopDTO.getShopAddress().getLocation().getLatitude(), shop.getShopAddress().getLocation().getLatitude());
        assertEquals(shopDTO.getShopAddress().getLocation().getLongitude(), shop.getShopAddress().getLocation().getLatitude());
    }
}

