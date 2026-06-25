package com.selcan.auth_system.service.impls;
import com.selcan.auth_system.dto.AuthResponseDto;
import com.selcan.auth_system.dto.LoginRequestDto;
import com.selcan.auth_system.dto.RegisterRequestDto;
import com.selcan.auth_system.dto.UserResponseDto;
import com.selcan.auth_system.repositories.RefreshTokenRepository;
import com.selcan.auth_system.repositories.UserRepository;
import com.selcan.auth_system.security.JwtService;
import com.selcan.auth_system.service.AuthService;
import org.springframework.stereotype.Service;
import com.selcan.auth_system.entity.RefreshToken;
import com.selcan.auth_system.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import com.selcan.auth_system.exception.ConflictException;
import com.selcan.auth_system.exception.ForbiddenException;
import com.selcan.auth_system.exception.UnauthorizedException;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    @Override
    public AuthResponseDto register(RegisterRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException(
                    "Email already in use"
            );
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException(
                    "Username already taken"
            );
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        user.setPasswordHash(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setIsActive(true);

        user = userRepository.save(user);

        String accessToken =
                jwtService.generateToken(user);

        String refreshToken =
                generateRefreshToken();

        RefreshToken token =
                RefreshToken.builder()
                        .user(user)
                        .token(refreshToken)
                        .expiresAt(
                                LocalDateTime.now()
                                        .plusDays(7)
                        )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .build();

        refreshTokenRepository.save(token);

        UserResponseDto userDto =
                modelMapper.map(
                        user,
                        UserResponseDto.class
                );

        return AuthResponseDto.builder()
                .success(true)
                .message("Registration successful")
                .user(userDto)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(900)
                .build();
    }
    @Transactional
    @Override
    public AuthResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByEmail(
                        request.getEmail()
                ).orElseThrow(
                        () -> new UnauthorizedException(
                                "Invalid email or password"
                        )
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash()
        )) {
            throw new UnauthorizedException(
                    "Invalid email or password"
            );
        }

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new ForbiddenException(
                    "Account is deactivated"
            );
        }

        refreshTokenRepository.deleteByUser(user);

        String accessToken =
                jwtService.generateToken(user);

        String refreshToken =
                generateRefreshToken();

        RefreshToken token =
                RefreshToken.builder()
                        .user(user)
                        .token(refreshToken)
                        .expiresAt(
                                LocalDateTime.now()
                                        .plusDays(7)
                        )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .build();

        refreshTokenRepository.save(token);

        UserResponseDto userDto =
                modelMapper.map(
                        user,
                        UserResponseDto.class
                );

        return AuthResponseDto.builder()
                .success(true)
                .message("Login successful")
                .user(userDto)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(900)
                .build();
    }

    private String generateRefreshToken() {

        byte[] bytes = new byte[64];

        new SecureRandom().nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }
}