package com.codity.distributed_job_scheduler.organization.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrganizationRequest {

    @NotBlank(message = "Organization name is required.")
    private String name;

    private String description;
}