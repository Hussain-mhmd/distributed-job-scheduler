package com.codity.distributed_job_scheduler.metrics.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetricsResponse {

    private Long totalOrganizations;
    private Long totalProjects;
    private Long totalQueues;

    private Long totalJobs;
    private Long pendingJobs;
    private Long runningJobs;
    private Long completedJobs;
    private Long failedJobs;
    private Long deadLetterJobs;

    private Double successRate;
}