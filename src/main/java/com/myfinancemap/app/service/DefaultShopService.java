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
import java.util.NoSuchElementException;

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
     * Method for getting the data of a selected Shop.
     *
     * @param shopId id of the shop
     * @return the selected shop as a dto.
     */
    @Override
    public ShopDto getShopById(final Long shopId) {
        final Shop shop = shopRepository.getShopByShopId(shopId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Bolt nem található!");
                });
        return shopMapper.toShopDto(shop);
    }

    /**
     * Method for creating a shop.
     *
     * @param createShopDto dto with data we want to create the shop with
     * @return the selected Shop as a dto.
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
     * Method for updating a shop.
     *
     * @param shopId        id of the shop.
     * @param createUpdateShopDto dto with date we want to update the shop with
     * @return the updated Shop as a dto.
     */
    @Override
    public ShopDto updateShop(final Long shopId, final CreateUpdateShopDto createUpdateShopDto) {
        final Shop shop = shopRepository.getShopByShopId(shopId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Profil nem található!");
                });
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
     * Method for deleting a shop.
     *
     * @param shopId id of the shop we want to delete.
     */
    @Override
    public void deleteShop(final Long shopId) {
        final Shop shop = shopRepository.getShopByShopId(shopId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Profil nem található!");
                });
        shopRepository.delete(shop);
        addressService.deleteAddress(shop.getAddress().getAddressId());
    }
}
