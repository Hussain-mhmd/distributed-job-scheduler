package com.codity.distributed_job_scheduler.executionlog.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExecutionLogResponse {

    private String logLevel;

    private String message;

    private LocalDateTime createdAt;
}