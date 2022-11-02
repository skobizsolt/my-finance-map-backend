package com.myfinancemap.app.dto.shop;

import com.myfinancemap.app.dto.address.AddressDto;
import com.myfinancemap.app.dto.businesscategory.BusinessCategoryDto;
import lombok.Data;

@Data
public class ShopDto {
    private Long shopId;
    private BusinessCategoryDto businessCategory;
    private String name;
    private String coordinateX;
    private String coordinateY;
    private AddressDto address;
}
