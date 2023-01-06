package com.scry.analystics.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO implements Serializable {
    @JsonIgnore
    private LocationDTO location;
    private Integer number;
    private String postCode;

    private Long id;
}
