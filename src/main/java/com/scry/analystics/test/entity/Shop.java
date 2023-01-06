package com.scry.analystics.test.entity;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "shop")
public class Shop {

    @Id
    private String shopName;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Address shopAddress;
}
