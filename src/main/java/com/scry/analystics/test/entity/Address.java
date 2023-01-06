package com.scry.analystics.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor

public class Address {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    Location location;
    @Column(name = "number")
    Integer number;
    @Column(name = "postcode")
    String postCode;
    @Id
    @GeneratedValue
    private Long id;

}
