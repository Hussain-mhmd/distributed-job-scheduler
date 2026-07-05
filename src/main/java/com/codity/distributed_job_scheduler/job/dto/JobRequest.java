package com.codity.distributed_job_scheduler.job.dto;

import com.codity.distributed_job_scheduler.common.enums.JobPriority;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class JobRequest {

    private UUID queueId;

    private String name;

    private String payload;

    private JobPriority priority;

    private Integer maxRetries;

    private LocalDateTime scheduledAt;
}