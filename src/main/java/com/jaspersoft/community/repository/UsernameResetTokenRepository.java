package com.jaspersoft.community.repository;

import com.jaspersoft.community.entity.UsernameResetToken;
import com.jaspersoft.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsernameResetTokenRepository extends JpaRepository<UsernameResetToken, Long> {

    Optional<UsernameResetToken> findByToken(String token);

    void deleteByUser(User user);

    Optional<UsernameResetToken> findByUser(User user);
}
