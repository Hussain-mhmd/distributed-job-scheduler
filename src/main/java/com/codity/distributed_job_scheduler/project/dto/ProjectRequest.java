package com.codity.distributed_job_scheduler.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ProjectRequest {

    @NotNull(message = "Organization ID is required.")
    private UUID organizationId;

    @NotBlank(message = "Project name is required.")
    private String name;

    private String description;
}