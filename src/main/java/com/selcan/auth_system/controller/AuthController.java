package com.selcan.auth_system.controller;

import com.selcan.auth_system.dto.*;
import com.selcan.auth_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(
            @Valid @RequestBody RegisterRequestDto request
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @Valid @RequestBody LoginRequestDto request
    ){
        return ResponseEntity.ok(
                authService.login(request)
        );
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> refreshToken(
            @Valid
            @RequestBody
            RefreshTokenRequestDto request
    ){
        return ResponseEntity.ok(
                authService.refreshToken(request)
        );
    }
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDto> logout(
            @Valid @RequestBody RefreshTokenRequestDto request
    ) {

        authService.logout(request);

        return ResponseEntity.ok(
                LogoutResponseDto.builder()
                        .success(true)
                        .message("Logged out successfully")
                        .build()
        );
    }
    @GetMapping("/me")
    public ResponseEntity<?> me(){

        Map<String, Object> response = new HashMap<>();

        response.put("success", true);
        response.put("user", authService.me());

        return ResponseEntity.ok(response);
    }
}
