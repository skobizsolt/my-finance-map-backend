package com.myfinancemap.app.dto.shop;

import com.myfinancemap.app.dto.address.UpdateAddressDto;
import lombok.Data;

@Data
public class CreateUpdateShopDto {
    private String name;
    private Long categoryId;
    private String coordinateX;
    private String coordinateY;
    private UpdateAddressDto address;
}
