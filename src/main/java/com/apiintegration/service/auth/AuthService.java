package com.apiintegration.service.auth;

import com.apiintegration.dto.Register.*;
import com.apiintegration.entity.Token.RefreshToken;
import com.apiintegration.entity.User.User;
import com.apiintegration.enums.Role;
import com.apiintegration.exception.ResourceAlreadyExistsException;
import com.apiintegration.repository.token.RefreshTokenRepository;
import com.apiintegration.repository.user.UserRepository;
import com.apiintegration.util.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

@Server
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    public ApiResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("User already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // 🔥 BCrypt
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return ApiResponse.success("User registered successfully");
    }

    //login
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return JwtService.generateToken(user);
    }

    public void logout(LogoutRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token"));

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    public TokenResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token"));

        if (refreshToken.isRevoked()) {
            throw new RuntimeException("Refresh token revoked");
        }

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        String newAccessToken = JwtService.generateToken(
                refreshToken.getUser()
        );

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }


}
