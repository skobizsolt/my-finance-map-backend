package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.profile.UpdateProfileDto;
import com.myfinancemap.app.mapper.ProfileMapper;
import com.myfinancemap.app.persistence.domain.Profile;
import com.myfinancemap.app.persistence.repository.ProfileRepository;
import com.myfinancemap.app.service.interfaces.AddressService;
import com.myfinancemap.app.service.interfaces.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Default implementation of Profile service.
 */
@Service
@AllArgsConstructor
public class DefaultProfileService implements ProfileService {

    @Autowired
    private final ProfileRepository profileRepository;
    @Autowired
    private final ProfileMapper profileMapper;
    @Autowired
    private final AddressService addressService;

    /**
     * @inheritDoc
     */
    @Override
    public Profile createProfile() {
        final Profile profile = new Profile();
        profile.setHomeAddress(addressService.createAddress());
        return profileRepository.save(profile);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updateProfile(final Long profileId, final UpdateProfileDto updateProfileDto) {
        final Profile profile = profileRepository.getProfileByProfileId(profileId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Profil nem található!");
                });
        // update address
        addressService.updateAddress(profile.getHomeAddress().getAddressId(), updateProfileDto.getAddress());
        // update profile
        profileMapper.modifyProfile(updateProfileDto, profile);
        profileRepository.saveAndFlush(profile);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteProfile(final Long profileId) {
        final Profile profile = profileRepository.getProfileByProfileId(profileId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Profil nem található!");
                });
        //deleting profile
        profileRepository.delete(profile);
        //deleting address
        addressService.deleteAddress(profile.getHomeAddress().getAddressId());
    }
}
