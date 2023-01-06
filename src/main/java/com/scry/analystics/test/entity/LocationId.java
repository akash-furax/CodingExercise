package com.scry.analystics.test.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class LocationId implements Serializable {

    private Double latitude;
    private Double longitude;
}
