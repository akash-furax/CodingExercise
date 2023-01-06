package com.scry.analystics.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "location")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(LocationId.class)
public class Location implements Serializable {

    @Id
    @Column(name = "lat")
    Double latitude;

    @Id
    @Column(name = "long")
    Double longitude;
}
