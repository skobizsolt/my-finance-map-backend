package com.myfinancemap.app.dto.user;

import com.myfinancemap.app.dto.profile.UpdateProfileDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UpdateUserDto {
    private UpdateProfileDto profile;
}
