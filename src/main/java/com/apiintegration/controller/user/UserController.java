package com.apiintegration.controller.user;

import com.apiintegration.util.ApiResponse;
import com.apiintegration.dto.Register.LoginRequest;
import com.apiintegration.dto.Register.LogoutRequest;
import com.apiintegration.dto.Register.RefreshTokenRequest;
import com.apiintegration.dto.auth.JwtResponse;
import com.apiintegration.entity.Token.RefreshToken;
import com.apiintegration.entity.User.User;
import com.apiintegration.repository.token.RefreshTokenRepository;
import com.apiintegration.repository.user.UserRepository;
import com.apiintegration.service.auth.JwtService;
import com.apiintegration.service.token.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public String userApi() {
        return "User access only";
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtService.generateToken(user);

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user.getId());

        return ResponseEntity.ok(
                new JwtResponse(accessToken, refreshToken.getToken())
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(
            @RequestBody RefreshTokenRequest request) {

        RefreshToken token = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        User user = token.getUser();

        String newAccessToken = jwtService.generateToken(user);

        return ResponseEntity.ok(
                new JwtResponse(newAccessToken, token.getToken())
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(
            @RequestBody LogoutRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        refreshTokenRepository.deleteByUser(user);

        return ResponseEntity.ok(
                ApiResponse.success("Logged out successfully")
        );
    }
}
