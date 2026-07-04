package com.codity.distributed_job_scheduler.queue.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class QueueRequest {

    @NotNull(message = "Project ID is required.")
    private UUID projectId;

    @NotBlank(message = "Queue name is required.")
    private String name;

    private String description;
}