package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.address.AddressDto;
import com.myfinancemap.app.dto.address.UpdateAddressDto;
import com.myfinancemap.app.mapper.AddressMapper;
import com.myfinancemap.app.persistence.domain.Address;
import com.myfinancemap.app.persistence.repository.AddressRepository;
import com.myfinancemap.app.service.interfaces.AddressService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Default implementation of the Address service.
 */
@Service
public class DefaultAddressService implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public DefaultAddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

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
