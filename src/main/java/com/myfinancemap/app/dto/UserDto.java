package com.myfinancemap.app.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends MinimalUserDto {
    private LocalDateTime registrationDate;
    private ProfileDto profile;
}
