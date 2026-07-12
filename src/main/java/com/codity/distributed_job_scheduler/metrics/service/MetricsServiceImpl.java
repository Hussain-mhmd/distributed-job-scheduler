package com.codity.distributed_job_scheduler.metrics.service;

import com.codity.distributed_job_scheduler.auth.entity.User;
import com.codity.distributed_job_scheduler.common.enums.JobStatus;
import com.codity.distributed_job_scheduler.common.util.SecurityUtil;
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
    private final SecurityUtil securityUtil;

    @Override
    public MetricsResponse getMetrics() {

        User currentUser = securityUtil.getCurrentUser();

        long totalOrganizations =
                organizationRepository.countByOwnerId(currentUser.getId());

        long totalProjects =
                projectRepository.countByOrganizationOwnerId(currentUser.getId());

        long totalQueues =
                queueRepository.countByProjectOrganizationOwnerId(currentUser.getId());

        long totalJobs =
                jobRepository.countByQueueProjectOrganizationOwnerId(currentUser.getId());

        long pendingJobs =
                jobRepository.countByStatusAndQueueProjectOrganizationOwnerId(
                        JobStatus.PENDING,
                        currentUser.getId());

        long runningJobs =
                jobRepository.countByStatusAndQueueProjectOrganizationOwnerId(
                        JobStatus.RUNNING,
                        currentUser.getId());

        long completedJobs =
                jobRepository.countByStatusAndQueueProjectOrganizationOwnerId(
                        JobStatus.COMPLETED,
                        currentUser.getId());

        long failedJobs =
                jobRepository.countByStatusAndQueueProjectOrganizationOwnerId(
                        JobStatus.FAILED,
                        currentUser.getId());

        long deadLetterJobs =
                deadLetterQueueRepository.countByJobQueueProjectOrganizationOwnerId(
                        currentUser.getId());

        double successRate =
                totalJobs == 0
                        ? 0
                        : (completedJobs * 100.0) / totalJobs;

        return MetricsResponse.builder()
                .totalOrganizations(totalOrganizations)
                .totalProjects(totalProjects)
                .totalQueues(totalQueues)
                .totalJobs(totalJobs)
                .pendingJobs(pendingJobs)
                .runningJobs(runningJobs)
                .completedJobs(completedJobs)
                .failedJobs(failedJobs)
                .deadLetterJobs(deadLetterJobs)
                .successRate(successRate)
                .build();
    }

}