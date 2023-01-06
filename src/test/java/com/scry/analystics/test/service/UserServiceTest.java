package com.scry.analystics.test.service;

import com.scry.analystics.test.entity.Shop;
import com.scry.analystics.test.repository.ShopRepository;
import com.scry.analystics.test.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private GeocodingService geocodingService;

    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private UserService userService;

    /**
     * Method under test: {@link UserService#findNearestLocationForUser(Double, Double)}
     */
    @Test
    void testFindNearestLocationForUser() {
        when(shopRepository.findAll()).thenReturn(List.of(TestUtil.createShop("11"),
                TestUtil.createShop("22"), TestUtil.createShop("33"), TestUtil.createShop("44")));
        userService.findNearestLocationForUser(10.0d, 10.0d);
    }

    /**
     * Method under test: {@link UserService#findNearestLocationForUser(Double, Double)}
     */
    @Test
    void testFindNearestLocationForUser2() {

        ArrayList<Shop> shopList = new ArrayList<>();
        Shop shop = TestUtil.createShop("123");
        shopList.add(shop);
        shopList.add(TestUtil.createShop("456"));
        shopList.add(TestUtil.createShop("789"));
        when(shopRepository.findAll()).thenReturn(shopList);
        when(geocodingService.calculateDistanceBetweenLatLong(10D, 123D, 10D, 123D)).thenReturn(10.0d);
        when(geocodingService.calculateDistanceBetweenLatLong(10D, 456D, 10D, 456D)).thenReturn(20.0d);
        when(geocodingService.calculateDistanceBetweenLatLong(10D, 789D, 10D, 789D)).thenReturn(30.0d);

        assertSame(shop, userService.findNearestLocationForUser(10.0d, 10.0d));
        verify(shopRepository).findAll();
        verify(geocodingService, times(3)).calculateDistanceBetweenLatLong((Double) any(), (Double) any(), (Double) any(),
                (Double) any());
    }
}

