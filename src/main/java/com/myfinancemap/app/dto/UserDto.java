package com.myfinancemap.app.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends MinimalUserDto {
    private ProfileDto profile;
}
