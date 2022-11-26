package com.myfinancemap.app.persistence.repository.auth;

import com.myfinancemap.app.persistence.domain.auth.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository
        extends JpaRepository<VerificationToken, Long> {
    @Query(value = "SELECT * FROM verification_token v WHERE v.token = :token", nativeQuery = true)
    Optional<VerificationToken> findByToken(final String token);
}
