package com.myfinancemap.app.persistence.repository.auth;

import com.myfinancemap.app.persistence.domain.auth.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository
        extends JpaRepository<PasswordResetToken, Long> {
    @Query(value = "SELECT * FROM password_token p WHERE p.token = :token", nativeQuery = true)
    Optional<PasswordResetToken> findByToken(final String token);
}
