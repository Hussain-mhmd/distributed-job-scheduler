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

@Service
@RequiredArgsConstructor
public class JobExecutionServiceImpl implements JobExecutionService {

    private final JobExecutionRepository repository;
    private final JobExecutionMapper mapper;

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

        return repository.findAllByOrderByStartedAtDesc()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<JobExecutionResponse> getByJob(UUID jobId) {

        return repository.findByJobIdOrderByStartedAtDesc(jobId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

}