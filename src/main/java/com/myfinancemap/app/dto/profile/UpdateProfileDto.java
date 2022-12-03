package com.myfinancemap.app.dto.profile;

import com.myfinancemap.app.dto.address.UpdateAddressDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
public class UpdateProfileDto {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private UpdateAddressDto address;
}
