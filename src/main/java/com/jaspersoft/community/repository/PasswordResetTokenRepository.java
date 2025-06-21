package com.jaspersoft.community.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaspersoft.community.entity.PasswordResetToken;
import com.jaspersoft.community.entity.User;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    // ✅ Delete token by user to avoid unique constraint violations
    void deleteByUser(User user);

    // ✅ Optional: Check if token exists for a user (useful for validations/debugging)
    Optional<PasswordResetToken> findByUser(User user);
} 
