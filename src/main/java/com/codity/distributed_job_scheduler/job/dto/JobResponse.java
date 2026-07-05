package com.codity.distributed_job_scheduler.job.dto;

import com.codity.distributed_job_scheduler.common.enums.JobPriority;
import com.codity.distributed_job_scheduler.common.enums.JobStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class JobResponse {

    private UUID id;

    private String name;

    private String payload;

    private UUID queueId;

    private String queueName;

    private JobStatus status;

    private JobPriority priority;

    private Integer retryCount;

    private Integer maxRetries;

    private LocalDateTime scheduledAt;

    private LocalDateTime executedAt;

    private LocalDateTime createdAt;
}