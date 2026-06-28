package com.selcan.auth_system.service;

import com.selcan.auth_system.dto.*;

public interface AuthService {
    AuthResponseDto register(RegisterRequestDto request);

    AuthResponseDto login(LoginRequestDto request);
    AuthResponseDto refreshToken(RefreshTokenRequestDto request);

    void logout(RefreshTokenRequestDto request);

    UserResponseDto me();
}
