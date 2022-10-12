package com.myfinancemap.app.dto;

import lombok.Data;

@Data
public class AddressDto {
    private Long addressId;
    private String postalCode;
    private String country;
    private String state;
    private String city;
    private String streetName;
    private String streetType;
    private String houseNumber;
    private String afterHouseNumber;
}
