package com.myfinancemap.app.dto.profile;

import com.myfinancemap.app.dto.address.UpdateAddressDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateProfileDto {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private UpdateAddressDto address;
}
