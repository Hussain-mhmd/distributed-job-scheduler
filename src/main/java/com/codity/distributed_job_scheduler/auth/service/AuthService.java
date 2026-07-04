package com.codity.distributed_job_scheduler.auth.service;

import com.codity.distributed_job_scheduler.auth.dto.AuthResponse;
import com.codity.distributed_job_scheduler.auth.dto.LoginRequest;
import com.codity.distributed_job_scheduler.auth.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}