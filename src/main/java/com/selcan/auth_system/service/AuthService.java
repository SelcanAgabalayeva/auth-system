package com.selcan.auth_system.service;

import com.selcan.auth_system.dto.AuthResponseDto;
import com.selcan.auth_system.dto.LoginRequestDto;
import com.selcan.auth_system.dto.RegisterRequestDto;

public interface AuthService {
    AuthResponseDto register(RegisterRequestDto request);

    AuthResponseDto login(LoginRequestDto request);
}
