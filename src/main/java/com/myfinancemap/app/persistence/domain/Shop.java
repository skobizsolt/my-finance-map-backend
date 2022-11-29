package com.myfinancemap.app.persistence.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private BusinessCategory businessCategory;
    @NotNull
    private String name;
    @OneToOne
    @JoinColumn(name = "locationId", referencedColumnName = "locationId")
    @NotNull
    private Location location;
    @ManyToOne
    @JoinColumn(name = "addressId", referencedColumnName = "addressId")
    private Address address;
}
