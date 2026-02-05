package com.apiintegration.controller.auth;

import com.apiintegration.util.ApiResponse;
import com.apiintegration.dto.Register.LogoutRequest;
import com.apiintegration.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(
            @RequestBody @Valid LogoutRequest request) {

        authService.logout(request);

        return ResponseEntity.ok(
                ApiResponse.success("Logout successful")
        );
    }
}
