package com.codity.distributed_job_scheduler.auth.service;

import com.codity.distributed_job_scheduler.auth.dto.AuthResponse;
import com.codity.distributed_job_scheduler.auth.dto.LoginRequest;
import com.codity.distributed_job_scheduler.auth.dto.RegisterRequest;
import com.codity.distributed_job_scheduler.auth.entity.User;
import com.codity.distributed_job_scheduler.auth.repository.UserRepository;
import com.codity.distributed_job_scheduler.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import com.codity.distributed_job_scheduler.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered.");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
    }
    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(request.getEmail());

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .build();
    }
}