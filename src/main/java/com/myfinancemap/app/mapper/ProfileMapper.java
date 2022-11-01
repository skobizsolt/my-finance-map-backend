package com.myfinancemap.app.mapper;

import com.myfinancemap.app.dto.profile.ProfileDto;
import com.myfinancemap.app.dto.profile.UpdateProfileDto;
import com.myfinancemap.app.persistence.domain.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(uses = AddressMapper.class)
@Component
public interface ProfileMapper {
    ProfileDto toProfileDto(Profile profile);

    void modifyProfile(UpdateProfileDto updateProfileDto, @MappingTarget Profile profile);
}
