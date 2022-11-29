package com.myfinancemap.app.service.interfaces;

import com.myfinancemap.app.dto.profile.UpdateProfileDto;
import com.myfinancemap.app.persistence.domain.Profile;

/**
 * Interface for Profile methods.
 */
public interface ProfileService {

    /**
     * Method for creating a new, empty profile for the newly created user.
     *
     * @return a new Profile entity.
     */
    Profile createProfile();

    /**
     * Method for update the given profile.
     *
     * @param userId           id of the user which linked to this profile
     * @param updateProfileDto the data we want to update the profile with.
     */
    void updateProfile(final Long userId, final UpdateProfileDto updateProfileDto);

    void deleteProfile(Long profileId);
}
