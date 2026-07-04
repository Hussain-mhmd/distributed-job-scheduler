package com.codity.distributed_job_scheduler.queue.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class QueueResponse {

    private UUID id;

    private String name;

    private String description;

    private UUID projectId;

    private String projectName;

    private LocalDateTime createdAt;
}