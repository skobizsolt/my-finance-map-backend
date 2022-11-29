package com.myfinancemap.app.mapper;

import com.myfinancemap.app.dto.address.AddressDto;
import com.myfinancemap.app.dto.address.UpdateAddressDto;
import com.myfinancemap.app.persistence.domain.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AddressMapper {
    AddressDto toAddressDto(Address address);

    void modifyAddress(UpdateAddressDto updateAddressDto, @MappingTarget Address address);
}
