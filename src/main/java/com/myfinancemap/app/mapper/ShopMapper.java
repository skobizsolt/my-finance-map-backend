package com.myfinancemap.app.mapper;

import com.myfinancemap.app.dto.shop.CreateUpdateShopDto;
import com.myfinancemap.app.dto.shop.ShopDto;
import com.myfinancemap.app.persistence.domain.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ShopMapper {
    ShopDto toShopDto(Shop shop);

    List<ShopDto> toShopDtoList(List<Shop> shops);

    Shop toShop(CreateUpdateShopDto createUpdateShopDto);

    Shop toShop(ShopDto createUpdateShopDto);

    void modifyShop(CreateUpdateShopDto shopDto, @MappingTarget Shop shop);
}
