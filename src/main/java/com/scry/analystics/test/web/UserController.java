package com.scry.analystics.test.web;

import com.scry.analystics.test.entity.Shop;
import com.scry.analystics.test.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    @ApiOperation(value = "Get Nearest Shop by Latitude and Longitude", response = ResponseEntity.class, httpMethod = "GET")
    public ResponseEntity getNearestShop(@ApiParam(value = "Latitude") @RequestParam(name = "lat", required = true) Double lat,
                                         @ApiParam(value = "Longitude") @RequestParam(name = "lon", required = true) Double lon) {
        Shop nearestLocationForUser = userService.findNearestLocationForUser(lat, lon);
        if (Objects.nonNull(nearestLocationForUser))
            return ResponseEntity.ok(nearestLocationForUser);
        else
            return ResponseEntity.status(417).build();
    }
}
