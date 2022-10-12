package com.myfinancemap.app.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProfileDto {
    private Long profileId;
    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;
    private AddressDto homeAddress;
}
