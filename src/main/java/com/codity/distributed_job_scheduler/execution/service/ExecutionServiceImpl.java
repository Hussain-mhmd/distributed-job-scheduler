package com.codity.distributed_job_scheduler.execution.service;

import com.codity.distributed_job_scheduler.common.enums.JobStatus;
import com.codity.distributed_job_scheduler.job.entity.Job;
import com.codity.distributed_job_scheduler.job.repository.JobRepository;
import com.codity.distributed_job_scheduler.retry.service.RetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExecutionServiceImpl implements ExecutionService {

    private final JobRepository jobRepository;
    private final RetryService retryService;

    @Async("jobExecutor")
    @Override
    public void execute(Job job) {

        job.setStatus(JobStatus.RUNNING);
        jobRepository.save(job);

        try {

            System.out.println(
                    Thread.currentThread().getName()
                            + " -> Executing : "
                            + job.getName());

            Thread.sleep(5000);

            if (Math.random() < 0.4) {
                throw new RuntimeException("Simulated Failure");
            }

            job.setStatus(JobStatus.COMPLETED);
            job.setExecutedAt(LocalDateTime.now());

        } catch (Exception e) {

            job.setStatus(JobStatus.FAILED);

            jobRepository.save(job);

            retryService.retry(job);

            return;

        }

        jobRepository.save(job);
    }
}