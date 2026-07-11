package com.codity.distributed_job_scheduler.jobexecution.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class JobExecutionResponse {

    private UUID id;

    private UUID jobId;

    private String jobName;

    private String workerName;

    private Integer attemptNumber;

    private String executionStatus;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    private Long executionTimeMs;

    private String errorMessage;
}