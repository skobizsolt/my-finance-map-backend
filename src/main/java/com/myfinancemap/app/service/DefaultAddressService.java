package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.address.AddressDto;
import com.myfinancemap.app.dto.address.UpdateAddressDto;
import com.myfinancemap.app.mapper.AddressMapper;
import com.myfinancemap.app.persistence.domain.Address;
import com.myfinancemap.app.persistence.repository.AddressRepository;
import com.myfinancemap.app.service.interfaces.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Default implementation of the Address service.
 */
@Service
@AllArgsConstructor
public class DefaultAddressService implements AddressService {

    @Autowired
    private final AddressRepository addressRepository;
    @Autowired
    private final AddressMapper addressMapper;

    /**
     * @inheritDoc
     */
    @Override
    public Address createAddress() {
        final Address address = new Address();
        return addressRepository.save(address);
    }

    /**
     * @inheritDoc
     */
    @Override
    public AddressDto updateAddress(Long addressId, UpdateAddressDto updateAddressDto) {
        final Address address = addressRepository.getAddressByAddressId(addressId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("A keresett cím nem található!");
                });
        addressMapper.modifyAddress(updateAddressDto, address);
        addressRepository.saveAndFlush(address);
        return addressMapper.toAddressDto(address);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteAddress(Long addressId) {
        final Address address = addressRepository.getAddressByAddressId(addressId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("A keresett cím nem található!");
                });
        addressRepository.delete(address);
    }
}
