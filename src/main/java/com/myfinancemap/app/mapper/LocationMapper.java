package com.myfinancemap.app.mapper;

import com.myfinancemap.app.dto.location.CreateUpdateLocationDto;
import com.myfinancemap.app.dto.location.LocationDto;
import com.myfinancemap.app.persistence.domain.Location;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface LocationMapper {
    LocationDto toLocationDto(Location location);

    Location toLocation(CreateUpdateLocationDto dto);

    void modifyAddress(CreateUpdateLocationDto updateLocationDto, @MappingTarget Location location);
}
