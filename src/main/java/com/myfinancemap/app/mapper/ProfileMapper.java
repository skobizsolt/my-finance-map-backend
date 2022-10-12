package com.myfinancemap.app.mapper;

import com.myfinancemap.app.dto.ProfileDto;
import com.myfinancemap.app.persistence.domain.Profile;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(uses = AddressMapper.class)
@Component
public interface ProfileMapper {
    ProfileDto profileToProfileDto(Profile profile);
}
