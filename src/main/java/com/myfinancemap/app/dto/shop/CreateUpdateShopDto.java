package com.myfinancemap.app.dto.shop;

import com.myfinancemap.app.dto.address.UpdateAddressDto;
import com.myfinancemap.app.dto.location.CreateUpdateLocationDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CreateUpdateShopDto {
    private String name;
    private Long categoryId;
    private CreateUpdateLocationDto location;
    private UpdateAddressDto address;
}
