package com.myfinancemap.app.persistence.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "shop")
@Getter
@Setter
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;
    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private BusinessCategory businessCategory;
    private String name;
    private String coordinateX;
    private String coordinateY;
    @ManyToOne
    @JoinColumn(name = "addressId", referencedColumnName = "addressId")
    private Address address;
}
