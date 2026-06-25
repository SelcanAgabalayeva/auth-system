package com.selcan.auth_system.controller;

import com.selcan.auth_system.dto.AuthResponseDto;
import com.selcan.auth_system.dto.LoginRequestDto;
import com.selcan.auth_system.dto.RegisterRequestDto;
import com.selcan.auth_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
