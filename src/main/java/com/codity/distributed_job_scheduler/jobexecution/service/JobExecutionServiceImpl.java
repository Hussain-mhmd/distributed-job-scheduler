package com.codity.distributed_job_scheduler.jobexecution.service;

import com.codity.distributed_job_scheduler.job.entity.Job;
import com.codity.distributed_job_scheduler.jobexecution.dto.JobExecutionResponse;
import com.codity.distributed_job_scheduler.jobexecution.entity.JobExecution;
import com.codity.distributed_job_scheduler.jobexecution.mapper.JobExecutionMapper;
import com.codity.distributed_job_scheduler.jobexecution.repository.JobExecutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import com.codity.distributed_job_scheduler.auth.entity.User;
import com.codity.distributed_job_scheduler.common.util.SecurityUtil;
import com.codity.distributed_job_scheduler.exception.BadRequestException;
import com.codity.distributed_job_scheduler.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class JobExecutionServiceImpl implements JobExecutionService {

    private final JobExecutionRepository repository;
    private final JobExecutionMapper mapper;
    private final SecurityUtil securityUtil;

    @Override
    public JobExecution startExecution(Job job) {

        JobExecution execution = JobExecution.builder()
                .job(job)
                .workerName(Thread.currentThread().getName())
                .attemptNumber(job.getRetryCount() + 1)
                .executionStatus("RUNNING")
                .startedAt(LocalDateTime.now())
                .build();

        return repository.save(execution);
    }

    @Override
    public void completeExecution(JobExecution execution) {

        execution.setExecutionStatus("COMPLETED");
        execution.setCompletedAt(LocalDateTime.now());

        execution.setExecutionTimeMs(
                Duration.between(
                                execution.getStartedAt(),
                                execution.getCompletedAt())
                        .toMillis());

        repository.save(execution);
    }

    @Override
    public void failExecution(JobExecution execution,
                              String error) {

        execution.setExecutionStatus("FAILED");
        execution.setCompletedAt(LocalDateTime.now());

        execution.setExecutionTimeMs(
                Duration.between(
                                execution.getStartedAt(),
                                execution.getCompletedAt())
                        .toMillis());

        execution.setErrorMessage(error);

        repository.save(execution);
    }
    @Override
    public List<JobExecutionResponse> getAll() {

        User currentUser = securityUtil.getCurrentUser();

        return repository
                .findByJobQueueProjectOrganizationOwnerIdOrderByStartedAtDesc(
                        currentUser.getId()
                )
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<JobExecutionResponse> getByJob(UUID jobId) {

        User currentUser = securityUtil.getCurrentUser();

        return repository
                .findByJobIdAndJobQueueProjectOrganizationOwnerIdOrderByStartedAtDesc(
                        jobId,
                        currentUser.getId()
                )
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

}