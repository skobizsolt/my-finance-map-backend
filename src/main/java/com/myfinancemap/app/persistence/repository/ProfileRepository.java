package com.myfinancemap.app.persistence.repository;

import com.myfinancemap.app.persistence.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> getProfileByProfileId(Long profileId);
}
