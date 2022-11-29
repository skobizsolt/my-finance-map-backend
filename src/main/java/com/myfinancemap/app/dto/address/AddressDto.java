package com.myfinancemap.app.dto.address;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AddressDto extends UpdateAddressDto {
    private Long addressId;
}
