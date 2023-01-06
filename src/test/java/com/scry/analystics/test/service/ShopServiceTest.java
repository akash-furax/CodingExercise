package com.scry.analystics.test.service;

import com.scry.analystics.test.entity.Shop;
import com.scry.analystics.test.model.AddressDTO;
import com.scry.analystics.test.model.LocationDTO;
import com.scry.analystics.test.model.ShopDTO;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ShopService.class})
@ExtendWith(MockitoExtension.class)
class ShopServiceTest {
    @Mock
    private GeocodingService geocodingService;

    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopService shopService;

    /**
     * Method under test: {@link ShopService#getAllShops()}
     */
    @Test
    void testGetAllShops() {
        when(shopRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(shopService.getAllShops().isEmpty());
        verify(shopRepository).findAll();
    }

    /**
     * Method under test: {@link ShopService#getAllShops()}
     */
    @Test
    void testGetAllShops2() {
        Shop shop = TestUtil.createShop("123");
        ArrayList<Shop> shopList = new ArrayList<>();
        shopList.add(shop);
        when(shopRepository.findAll()).thenReturn(shopList);
        List<ShopDTO> actualAllShops = shopService.getAllShops();
        assertEquals(1, actualAllShops.size());
        ShopDTO getResult = actualAllShops.get(0);
        assertEquals("123", getResult.getShopName());
        AddressDTO shopAddress = getResult.getShopAddress();
        assertEquals("123", shopAddress.getPostCode());
        assertEquals(123, shopAddress.getNumber().intValue());
        assertEquals(123L, shopAddress.getId().longValue());
        LocationDTO location1 = shopAddress.getLocation();
        assertEquals(123d, location1.getLongitude().doubleValue());
        assertEquals(123d, location1.getLatitude().doubleValue());
        verify(shopRepository).findAll();
    }

    /**
     * Method under test: {@link ShopService#getAllShops()}
     */
    @Test
    void testGetAllShops3() {
        Shop shop1 = TestUtil.createShop("123");
        Shop shop2 = TestUtil.createShop("1234");

        ArrayList<Shop> shopList = new ArrayList<>();
        shopList.add(shop1);
        shopList.add(shop2);
        when(shopRepository.findAll()).thenReturn(shopList);
        List<ShopDTO> actualAllShops = shopService.getAllShops();
        assertEquals(2, actualAllShops.size());
        ShopDTO getResult = actualAllShops.get(0);
        assertEquals("123", getResult.getShopName());
        ShopDTO getResult1 = actualAllShops.get(1);
        assertEquals("1234", getResult1.getShopName());
        AddressDTO shopAddress = getResult1.getShopAddress();
        AddressDTO shopAddress1 = getResult.getShopAddress();
        assertEquals("1234", shopAddress.getPostCode());
        assertEquals("123", shopAddress1.getPostCode());
        assertEquals(123, shopAddress1.getNumber().intValue());
        assertEquals(123L, shopAddress1.getId().longValue());
        assertEquals(1234L, shopAddress.getId().longValue());
        assertEquals(1234, shopAddress.getNumber().intValue());
        LocationDTO location2 = shopAddress1.getLocation();
        LocationDTO location3 = shopAddress.getLocation();
        assertEquals(123d, location2.getLongitude().doubleValue());
        assertEquals(1234d, location3.getLongitude().doubleValue());
        assertEquals(1234d, location3.getLatitude().doubleValue());
        assertEquals(123d, location2.getLatitude().doubleValue());
        verify(shopRepository).findAll();
    }

    /**
     * Method under test: {@link ShopService#updateShopIfExists(ShopDTO)}
     */
    @Test
    void testUpdateShopIfExists() {
        Shop shop1 = TestUtil.createShop("123");
        Optional<Shop> ofResult = Optional.of(shop1);
        Shop shop2 = TestUtil.createShop("1234");
        when(shopRepository.save((Shop) any())).thenReturn(shop2);
        when(shopRepository.findById((String) any())).thenReturn(ofResult);
        when(geocodingService.retrieveLatLongForAddress((String) any())).thenReturn(new LocationDTO());

        ShopDTO actualUpdateShopIfExistsResult = shopService.updateShopIfExists(ObjectMapperUtils.map(shop1, ShopDTO.class));
        assertEquals("123", shop1.getShopName());
        AddressDTO shopAddress = actualUpdateShopIfExistsResult.getShopAddress();
        assertEquals("1234", shopAddress.getPostCode());
        assertEquals(1234, shopAddress.getNumber().intValue());
        assertEquals(1234L, shopAddress.getId().longValue());
        LocationDTO location2 = shopAddress.getLocation();
        assertEquals(1234d, location2.getLongitude().doubleValue());
        assertEquals(1234d, location2.getLatitude().doubleValue());
        verify(shopRepository).save((Shop) any());
        verify(shopRepository).findById((String) any());
        verify(geocodingService).retrieveLatLongForAddress((String) any());
    }

    /**
     * Method under test: {@link ShopService#updateShopIfExists(ShopDTO)}
     */
    @Test
    void testUpdateShopIfExists2() {
        Shop shop = TestUtil.createShop("123");
        when(shopRepository.save((Shop) any())).thenReturn(shop);
        when(shopRepository.findById((String) any())).thenReturn(Optional.empty());
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLongitude(10d);
        locationDTO.setLongitude(10d);
        when(geocodingService.retrieveLatLongForAddress((String) any())).thenReturn(locationDTO);

        ShopDTO actualUpdateShopIfExistsResult = shopService.updateShopIfExists(ObjectMapperUtils.map(shop, ShopDTO.class));
        assertEquals("123", actualUpdateShopIfExistsResult.getShopName());
        AddressDTO shopAddress = actualUpdateShopIfExistsResult.getShopAddress();
        assertEquals("123", shopAddress.getPostCode());
        assertEquals(123, shopAddress.getNumber().intValue());
        assertEquals(123L, shopAddress.getId().longValue());
        LocationDTO location3 = shopAddress.getLocation();
        assertEquals(123d, location3.getLongitude().doubleValue());
        assertEquals(123d, location3.getLatitude().doubleValue());
        verify(shopRepository).save((Shop) any());
        verify(shopRepository).findById((String) any());
        verify(geocodingService).retrieveLatLongForAddress((String) any());
        assertEquals(shop.getShopAddress().getLocation().getLatitude(), actualUpdateShopIfExistsResult.getShopAddress().getLocation().getLatitude());
        assertEquals(shop.getShopAddress().getLocation().getLongitude(), actualUpdateShopIfExistsResult.getShopAddress().getLocation().getLongitude());
    }
}

