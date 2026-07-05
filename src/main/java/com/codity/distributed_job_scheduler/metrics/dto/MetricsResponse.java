package com.codity.distributed_job_scheduler.metrics.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricsResponse {

    private long totalJobs;
    private long pendingJobs;
    private long completedJobs;
    private long failedJobs;
    private long deadLetterJobs;
}