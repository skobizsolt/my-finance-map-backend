package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.shop.CreateUpdateShopDto;
import com.myfinancemap.app.dto.shop.ShopDto;
import com.myfinancemap.app.mapper.ShopMapper;
import com.myfinancemap.app.persistence.domain.Shop;
import com.myfinancemap.app.persistence.repository.ShopRepository;
import com.myfinancemap.app.service.interfaces.AddressService;
import com.myfinancemap.app.service.interfaces.BusinessCategoryService;
import com.myfinancemap.app.service.interfaces.ShopService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultShopService implements ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final AddressService addressService;
    private final BusinessCategoryService businessCategoryService;

    public DefaultShopService(ShopRepository shopRepository,
                              ShopMapper shopMapper,
                              AddressService addressService,
                              BusinessCategoryService businessCategoryService) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
        this.addressService = addressService;
        this.businessCategoryService = businessCategoryService;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<ShopDto> getAllShops() {
        return shopMapper.toShopDtoList(shopRepository.findAll());
    }

    /**
     * @inheritDoc
     */
    @Override
    public ShopDto getShopById(final Long shopId) {
        final Shop shop = getShopEntityById(shopId);
        return shopMapper.toShopDto(shop);
    }

    /**
     * @inheritDoc
     */
    @Override
    public ShopDto createShop(final CreateUpdateShopDto createShopDto) {
        final Shop shop = shopMapper.toShop(createShopDto);
        shop.setBusinessCategory(
                businessCategoryService.getBusinessEntityById(createShopDto.getCategoryId()));
        // create new address
        shop.setAddress(addressService.createAddress());
        // create the new shop
        shopRepository.save(shop);
        return shopMapper.toShopDto(shop);
    }

    /**
     * @inheritDoc
     */
    @Override
    public ShopDto updateShop(final Long shopId, final CreateUpdateShopDto createUpdateShopDto) {
        final Shop shop = getShopEntityById(shopId);
        // updating the address
        addressService.updateAddress(shop.getAddress().getAddressId(), createUpdateShopDto.getAddress());
        // get business category
        shop.setBusinessCategory(
                businessCategoryService.getBusinessEntityById(createUpdateShopDto.getCategoryId()));
        // modify and save the new shop
        shopMapper.modifyShop(createUpdateShopDto, shop);
        shopRepository.saveAndFlush(shop);
        return shopMapper.toShopDto(shop);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteShop(final Long shopId) {
        final Shop shop = getShopEntityById(shopId);
        shopRepository.delete(shop);
        addressService.deleteAddress(shop.getAddress().getAddressId());
    }

    /**
     * @inheritDoc
     */
    @Override
    public Shop getShopEntityById(final Long shopId) {
        return shopRepository.getShopByShopId(shopId).orElse(null);
    }
}
