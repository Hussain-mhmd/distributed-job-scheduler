package com.codity.distributed_job_scheduler.auth.controller;

import com.codity.distributed_job_scheduler.auth.dto.AuthResponse;
import com.codity.distributed_job_scheduler.auth.dto.LoginRequest;
import com.codity.distributed_job_scheduler.auth.dto.RegisterRequest;
import com.codity.distributed_job_scheduler.auth.service.AuthService;
import com.codity.distributed_job_scheduler.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Authentication APIs",
        description = "User authentication APIs"
)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register User")

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> register(@Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ApiResponse.success(
                "User registered successfully.",
                null
        );
    }
    @Operation(summary = "Login User")

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Login successful.",
                        authService.login(request)
                )
        );
    }
}