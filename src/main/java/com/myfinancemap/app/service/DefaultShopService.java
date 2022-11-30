package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.ShopCoordinateResponse;
import com.myfinancemap.app.dto.shop.CreateUpdateShopDto;
import com.myfinancemap.app.dto.shop.ShopDto;
import com.myfinancemap.app.mapper.ShopMapper;
import com.myfinancemap.app.persistence.domain.Shop;
import com.myfinancemap.app.persistence.repository.ShopRepository;
import com.myfinancemap.app.service.interfaces.AddressService;
import com.myfinancemap.app.service.interfaces.BusinessCategoryService;
import com.myfinancemap.app.service.interfaces.LocationService;
import com.myfinancemap.app.service.interfaces.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Default implementation of the Shop service.
 */
@Service
@AllArgsConstructor
public class DefaultShopService implements ShopService {

    @Autowired
    private final ShopRepository shopRepository;
    @Autowired
    private final ShopMapper shopMapper;
    @Autowired
    private final AddressService addressService;
    @Autowired
    private final BusinessCategoryService businessCategoryService;
    @Autowired
    private final LocationService locationService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShopDto> getAllShops() {
        return shopMapper.toShopDtoList(shopRepository.findAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopDto getShopById(final Long shopId) {
        final Shop shop = getShopEntityById(shopId);
        return shopMapper.toShopDto(shop);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopDto createShop(final CreateUpdateShopDto createShopDto) {
        final Shop shop = shopMapper.toShop(createShopDto);
        shop.setBusinessCategory(
                businessCategoryService.getBusinessEntityById(createShopDto.getCategoryId()));
        // create new address
        shop.setAddress(addressService.createAddress());
        //add location
        locationService.createLocation(shop.getLocation());
        // create the new shop
        shopRepository.save(shop);
        return shopMapper.toShopDto(shop);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopDto updateShop(final Long shopId, final CreateUpdateShopDto createUpdateShopDto) {
        final Shop shop = getShopEntityById(shopId);
        // updating the address
        addressService.updateAddress(shop.getAddress().getAddressId(), createUpdateShopDto.getAddress());
        // updating location
        locationService.updateLocation(shop.getLocation().getLocationId(), createUpdateShopDto.getLocation());
        // get business category
        shop.setBusinessCategory(
                businessCategoryService.getBusinessEntityById(createUpdateShopDto.getCategoryId()));
        // modify and save the new shop
        shopMapper.modifyShop(createUpdateShopDto, shop);
        shopRepository.saveAndFlush(shop);
        return shopMapper.toShopDto(shop);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteShop(final Long shopId) {
        final Shop shop = getShopEntityById(shopId);
        shopRepository.delete(shop);
        addressService.deleteAddress(shop.getAddress().getAddressId());
        locationService.deleteLocation(shop.getLocation().getLocationId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shop getShopEntityById(final Long shopId) {
        return shopRepository.getShopByShopId(shopId).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShopCoordinateResponse> getShopCoordinates() {
        return shopMapper.toListResponse(shopRepository.findAll());
    }
}
