package com.myfinancemap.app.mapper;

import com.myfinancemap.app.dto.AddressDto;
import com.myfinancemap.app.persistence.domain.Address;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AddressMapper {
    AddressDto addressToAddressDto (Address address);
}
