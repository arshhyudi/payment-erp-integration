package com.apiintegration.service.token;

import com.apiintegration.entity.Token.RefreshToken;
import com.apiintegration.entity.User.User;
import com.apiintegration.repository.token.RefreshTokenRepository;
import com.apiintegration.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository repository;
    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow();

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(7, ChronoUnit.DAYS))
                .build();

        return repository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {

        if (token.getExpiryDate().isBefore(Instant.now())) {
            repository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }
        return token;
    }
}
