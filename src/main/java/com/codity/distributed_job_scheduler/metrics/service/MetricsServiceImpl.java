package com.codity.distributed_job_scheduler.metrics.service;

import com.codity.distributed_job_scheduler.common.enums.JobStatus;
import com.codity.distributed_job_scheduler.deadletter.repository.DeadLetterQueueRepository;
import com.codity.distributed_job_scheduler.job.repository.JobRepository;
import com.codity.distributed_job_scheduler.metrics.dto.MetricsResponse;
import com.codity.distributed_job_scheduler.organization.repository.OrganizationRepository;
import com.codity.distributed_job_scheduler.project.repository.ProjectRepository;
import com.codity.distributed_job_scheduler.queue.repository.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricsServiceImpl implements MetricsService {

    private final JobRepository jobRepository;
    private final QueueRepository queueRepository;
    private final ProjectRepository projectRepository;
    private final OrganizationRepository organizationRepository;
    private final DeadLetterQueueRepository deadLetterQueueRepository;

    @Override
    public MetricsResponse getMetrics() {

        long totalJobs = jobRepository.count();
        long completedJobs = jobRepository.countByStatus(JobStatus.COMPLETED);

        double successRate = totalJobs == 0
                ? 0.0
                : (completedJobs * 100.0) / totalJobs;

        return MetricsResponse.builder()
                .totalOrganizations(organizationRepository.count())
                .totalProjects(projectRepository.count())
                .totalQueues(queueRepository.count())

                .totalJobs(totalJobs)
                .pendingJobs(jobRepository.countByStatus(JobStatus.PENDING))
                .runningJobs(jobRepository.countByStatus(JobStatus.RUNNING))
                .completedJobs(completedJobs)
                .failedJobs(jobRepository.countByStatus(JobStatus.FAILED))
                .deadLetterJobs(deadLetterQueueRepository.count())

                .successRate(successRate)
                .build();
    }
}