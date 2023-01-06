package com.scry.analystics.test.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO {

    private String shopName;
    private AddressDTO shopAddress;

}
