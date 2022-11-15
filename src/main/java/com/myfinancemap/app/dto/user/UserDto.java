package com.myfinancemap.app.dto.user;

import com.myfinancemap.app.dto.profile.ProfileDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserDto extends MinimalUserDto {
    private LocalDateTime registrationDate;
    private ProfileDto profile;
}
