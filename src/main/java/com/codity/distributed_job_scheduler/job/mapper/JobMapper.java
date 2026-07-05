package com.codity.distributed_job_scheduler.job.mapper;

import com.codity.distributed_job_scheduler.job.dto.JobResponse;
import com.codity.distributed_job_scheduler.job.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    public JobResponse toResponse(Job job) {

        return JobResponse.builder()
                .id(job.getId())
                .name(job.getName())
                .payload(job.getPayload())
                .queueId(job.getQueue().getId())
                .queueName(job.getQueue().getName())
                .status(job.getStatus())
                .priority(job.getPriority())
                .retryCount(job.getRetryCount())
                .maxRetries(job.getMaxRetries())
                .scheduledAt(job.getScheduledAt())
                .executedAt(job.getExecutedAt())
                .createdAt(job.getCreatedAt())
                .build();
    }
}