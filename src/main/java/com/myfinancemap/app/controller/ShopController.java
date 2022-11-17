package com.myfinancemap.app.controller;

import com.myfinancemap.app.dto.ShopCoordinateResponse;
import com.myfinancemap.app.dto.shop.CreateUpdateShopDto;
import com.myfinancemap.app.dto.shop.ShopDto;
import com.myfinancemap.app.service.interfaces.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/shops")
@Slf4j
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    /**
     * Controller for listing all shops that exist in the database.
     *
     * @return a List of Shops.
     */
    @GetMapping
    @Operation(summary = "List all shops that exist in the DB")
    public ResponseEntity<List<ShopDto>> getAllShops() {
        log.info("Endpoint invoked.");
        return ResponseEntity.ok().body(shopService.getAllShops());
    }

    /**
     * Controller for listing all shops that exist in the database.
     *
     * @param shopId id of the user
     * @return the selected Shop in dto form.
     */
    @GetMapping(value = "/{shopId}")
    @Operation(summary = "List all shops that exist in the DB")
    public ResponseEntity<ShopDto> getAllShops(@PathVariable final Long shopId) {
        log.info("Endpoint invoked. shopId = {}", shopId);
        return ResponseEntity.ok().body(shopService.getShopById(shopId));
    }

    /**
     * Controller for creating a shop.
     *
     * @param createShopDto given data for creating the new shop
     * @return the created Shop as a dto.
     */
    @PostMapping(value = "/new")
    @Operation(summary = "Create new shop")
    public ResponseEntity<ShopDto> createShop(@Valid @RequestBody final CreateUpdateShopDto createShopDto) {
        log.info("Endpoint invoked. createShopDto = {}", createShopDto);
        return ResponseEntity.ok().body(shopService.createShop(createShopDto));
    }

    /**
     * Controller for updating an existing shop.
     *
     * @param shopId              id of the shop we want to update
     * @param createUpdateShopDto given data for modify the shop data.
     * @return the updated Shop as a dto.
     */
    @PutMapping(value = "/update")
    @Operation(summary = "Update an existing shop")
    public ResponseEntity<ShopDto> updateTransaction(@RequestParam final Long shopId,
                                                     @Valid @RequestBody final CreateUpdateShopDto createUpdateShopDto) {
        log.info("Endpoint invoked. shopId = {}, updateShopDto = {}", shopId, createUpdateShopDto);
        return ResponseEntity.ok().body(shopService.updateShop(shopId, createUpdateShopDto));
    }

    /**
     * Controller to delete the selected shop
     *
     * @param shopId id of the shop
     * @return 200 OK if the shop is found.
     */
    @DeleteMapping(value = "/delete/{shopId}")
    @Operation(summary = "Delete an existing shop")
    public ResponseEntity<Void> deleteShop(@PathVariable final Long shopId) {
        log.info("Endpoint invoked. shopId = {}", shopId);
        shopService.deleteShop(shopId);
        return ResponseEntity.ok().build();
    }

    /**
     * Controller for getting public shop coordinates.
     *
     * @return List of all shop coordinates and ids.
     */
    @GetMapping("/map")
    @Operation(summary = "List all shops coordinates and ids")
    public ResponseEntity<List<ShopCoordinateResponse>> getShopCoordinates() {
        log.info("Endpoint invoked.");
        return ResponseEntity.ok().body(shopService.getShopCoordinates());
    }
}
