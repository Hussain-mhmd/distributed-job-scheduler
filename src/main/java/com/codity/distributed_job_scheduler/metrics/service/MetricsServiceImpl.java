package com.codity.distributed_job_scheduler.metrics.service;

import com.codity.distributed_job_scheduler.deadletter.repository.DeadLetterQueueRepository;
import com.codity.distributed_job_scheduler.common.enums.JobStatus;
import com.codity.distributed_job_scheduler.job.repository.JobRepository;
import com.codity.distributed_job_scheduler.metrics.dto.MetricsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricsServiceImpl implements MetricsService {

    private final JobRepository jobRepository;
    private final DeadLetterQueueRepository deadLetterQueueRepository;

    @Override
    public MetricsResponse getMetrics() {

        return MetricsResponse.builder()
                .totalJobs(jobRepository.count())
                .pendingJobs(jobRepository.countByStatus(JobStatus.PENDING))
                .completedJobs(jobRepository.countByStatus(JobStatus.COMPLETED))
                .failedJobs(jobRepository.countByStatus(JobStatus.FAILED))
                .deadLetterJobs(deadLetterQueueRepository.count())
                .build();
    }
}