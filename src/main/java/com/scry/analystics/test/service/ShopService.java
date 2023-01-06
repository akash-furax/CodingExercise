package com.scry.analystics.test.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scry.analystics.test.entity.Location;
import com.scry.analystics.test.entity.Shop;
import com.scry.analystics.test.model.AddressDTO;
import com.scry.analystics.test.model.LocationDTO;
import com.scry.analystics.test.model.ShopDTO;
import com.scry.analystics.test.repository.ShopRepository;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopService {
    private final ShopRepository shopRepository;

    private final GeocodingService geocodingService;

    private final ModelMapper modelMapper;

    private final ObjectMapper mapper;

    public ShopService(ShopRepository shopRepository, GeocodingService geocodingService, ModelMapper modelMapper, ObjectMapper mapper) {
        this.shopRepository = shopRepository;
        this.geocodingService = geocodingService;
        this.modelMapper = modelMapper;
        this.mapper = mapper;
    }

    public List<ShopDTO> getAllShops() {
        //TODO improvise
        return shopRepository.findAll()
                .stream().map(shop -> modelMapper.map(shop, ShopDTO.class)).collect(Collectors.toList());
    }


    @SneakyThrows
    public ShopDTO updateShopIfExists(ShopDTO shopDTO) {
        Optional<Shop> optionalShop = shopRepository.findById(shopDTO.getShopName());
        Shop oldValue = null;
        LocationDTO locationdto = retrieveLatLongForAddress(shopDTO.getShopAddress());
        Shop shop = null;
        if (optionalShop.isPresent()) {
            shop = optionalShop.get();
            oldValue = mapper.readValue(mapper.writeValueAsString(shop), Shop.class);
            ;
            // Can upgrade to modelmapper.update() but ignoring for now.
            shop.getShopAddress().setLocation(modelMapper.map(locationdto, Location.class));
            shop.getShopAddress().setPostCode(shopDTO.getShopAddress().getPostCode());
            shop.getShopAddress().setNumber(shopDTO.getShopAddress().getNumber());
            updateShop(shop);
        } else {
            shopDTO.getShopAddress().setLocation(locationdto);
            shop = updateShop(modelMapper.map(shopDTO, Shop.class));
        }

        return optionalShop.isPresent() ? modelMapper.map(oldValue, ShopDTO.class) : modelMapper.map(shop, ShopDTO.class);
    }

    private LocationDTO retrieveLatLongForAddress(AddressDTO shopAddress) {
        return geocodingService.retrieveLatLongForAddress(shopAddress.getPostCode());
    }

    @Transactional
    private Shop updateShop(Shop shop) {
        return shopRepository.save(shop);
    }
}
