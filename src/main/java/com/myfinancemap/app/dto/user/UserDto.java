package com.myfinancemap.app.dto.user;

import com.myfinancemap.app.dto.profile.ProfileDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends MinimalUserDto {
    private LocalDateTime registrationDate;
    private ProfileDto profile;
}
