package com.codity.distributed_job_scheduler.worker.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class WorkerResponse {

    private UUID id;

    private String workerName;

    private String hostName;

    private String workerStatus;

    private Integer activeJobs;

    private LocalDateTime lastHeartbeat;

}