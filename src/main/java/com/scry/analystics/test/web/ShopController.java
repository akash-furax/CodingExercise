package com.scry.analystics.test.web;

import com.scry.analystics.test.model.ShopDTO;
import com.scry.analystics.test.service.ShopService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    //update shop info here.
    @PostMapping
    @ApiOperation(value = "Update shop info", response = ResponseEntity.class, httpMethod = "POST")
    @ApiParam()
    public ResponseEntity<ShopDTO> updateShop(@ApiParam(value = "Shop details") @RequestBody ShopDTO shopDTO) {
        return ResponseEntity.ok(shopService.updateShopIfExists(shopDTO));
    }

    //API to get all shops for testing
    @GetMapping
    @ApiOperation(value = "Get All Shops", response = ResponseEntity.class, httpMethod = "GET")
    public ResponseEntity<List<ShopDTO>> getAllShops() {
        return ResponseEntity.ok(shopService.getAllShops());
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ShopDTO>> batchUpdate(@RequestBody List<ShopDTO> shopDTOs) {
        return ResponseEntity.ok(shopDTOs.stream().map(shopDTO -> shopService.updateShopIfExists(shopDTO)).collect(Collectors.toList()));
    }

}
