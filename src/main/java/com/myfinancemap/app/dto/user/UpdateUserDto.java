package com.myfinancemap.app.dto.user;

import com.myfinancemap.app.dto.profile.UpdateProfileDto;
import lombok.Data;

@Data
public class UpdateUserDto {
    private UpdateProfileDto profile;
}
