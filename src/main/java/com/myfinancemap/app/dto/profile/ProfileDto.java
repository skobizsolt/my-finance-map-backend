package com.myfinancemap.app.dto.profile;

import com.myfinancemap.app.dto.address.AddressDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileDto {
    private Long profileId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private AddressDto homeAddress;
}
