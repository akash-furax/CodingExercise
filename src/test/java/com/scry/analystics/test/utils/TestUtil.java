package com.scry.analystics.test.utils;

import com.scry.analystics.test.entity.Address;
import com.scry.analystics.test.entity.Location;
import com.scry.analystics.test.entity.Shop;

public class TestUtil {
    public static Shop createShop(String i) {
        Shop shop = new Shop();
        Address address = new Address();
        Location location = new Location();
        location.setLatitude(Double.parseDouble(i));
        location.setLongitude(Double.parseDouble(i));
        address.setLocation(location);
        address.setId(Long.parseLong(i));
        address.setNumber(Integer.parseInt(i));
        address.setPostCode(i);
        shop.setShopAddress(address);
        shop.setShopName(i);
        return shop;
    }
}
