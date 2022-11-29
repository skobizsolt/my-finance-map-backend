package com.myfinancemap.app.service.interfaces;

import com.myfinancemap.app.dto.address.AddressDto;
import com.myfinancemap.app.dto.address.UpdateAddressDto;
import com.myfinancemap.app.persistence.domain.Address;

/**
 * Interface for methods of Address service.
 */
public interface AddressService {
    /**
     * Method for creating a new, empty address.
     *
     * @return a new empty Address, with an id.
     */
    Address createAddress();

    /**
     * Method for updating the given address.
     *
     * @param addressId        id of the selected address.
     * @param updateAddressDto data we want to modify the address with
     * @return a dto, containing the updated Address.
     */
    AddressDto updateAddress(final Long addressId, final UpdateAddressDto updateAddressDto);

    /**
     * Method for deleting the selected address from the repository.
     *
     * @param addressId id of the address we want to delete.
     */
    void deleteAddress(final Long addressId);
}
