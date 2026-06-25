package com.selcan.auth_system.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponseDto {

    private boolean success;

    private String message;

    private UserResponseDto user;

    private String accessToken;

    private String refreshToken;

    private long expiresIn;
}
