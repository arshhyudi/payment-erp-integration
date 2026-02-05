package com.apiintegration.repository.token;

import com.apiintegration.entity.Token.RefreshToken;
import com.apiintegration.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}
