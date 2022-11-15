package com.myfinancemap.app.dto.shop;

import com.myfinancemap.app.dto.address.UpdateAddressDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CreateUpdateShopDto {
    private String name;
    private Long categoryId;
    private String coordinateX;
    private String coordinateY;
    private UpdateAddressDto address;
}
