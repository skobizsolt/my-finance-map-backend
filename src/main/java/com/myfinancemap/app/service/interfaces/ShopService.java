package com.myfinancemap.app.service.interfaces;

import com.myfinancemap.app.dto.ShopCoordinateResponse;
import com.myfinancemap.app.dto.shop.CreateUpdateShopDto;
import com.myfinancemap.app.dto.shop.ShopDto;
import com.myfinancemap.app.persistence.domain.Shop;

import java.util.List;

/**
 * Interface for the implementation of Shop service.
 */
public interface ShopService {
    /**
     * Method for getting all existing shops.
     *
     * @return a List of Shop data
     */
    List<ShopDto> getAllShops();

    /**
     * Method for getting the data of a selected Shop.
     *
     * @param shopId id of the shop
     * @return the selected shop as a dto.
     */
    ShopDto getShopById(final Long shopId);

    /**
     * Method for creating a shop.
     *
     * @param createShopDto dto with data we want to create the shop with
     * @return the new Shop as a dto.
     */
    ShopDto createShop(final CreateUpdateShopDto createShopDto);

    /**
     * Method for updating a shop.
     *
     * @param shopId id of the shop.
     * @param createUpdateShopDto dto with date we want to update the shop with
     * @return the updated Shop as a dto.
     */
    ShopDto updateShop(final Long shopId, final CreateUpdateShopDto createUpdateShopDto);

    /**
     * Method for deleting a shop.
     *
     * @param shopId id of the shop we want to delete.
     */
    void deleteShop(final Long shopId);

    /**
     * Method for getting the selected entity from repo.
     *
     * @param shopId id of the shop
     * @return the selected Shop entity.
     */
    Shop getShopEntityById(final Long shopId);

    /**
     * Method for getting shop coordinates and its id's for map integration.
     *
     * @return List, containing coordinates and shopIds.
     */
    List<ShopCoordinateResponse> getShopCoordinates();

}
