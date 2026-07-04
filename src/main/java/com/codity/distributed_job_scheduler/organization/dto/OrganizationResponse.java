package com.codity.distributed_job_scheduler.organization.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class OrganizationResponse {

    private UUID id;

    private String name;

    private String description;

    private String ownerName;

    private String ownerEmail;

    private LocalDateTime createdAt;
}