package com.codity.distributed_job_scheduler.deadletter.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeadLetterQueueResponse {

    private UUID id;

    private UUID jobId;

    private String jobName;

    private String failureReason;

    private LocalDateTime failedAt;

    private LocalDateTime createdAt;
}