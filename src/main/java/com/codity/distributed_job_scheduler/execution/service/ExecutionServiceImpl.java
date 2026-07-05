package com.codity.distributed_job_scheduler.execution.service;

import com.codity.distributed_job_scheduler.common.enums.JobStatus;
import com.codity.distributed_job_scheduler.job.entity.Job;
import com.codity.distributed_job_scheduler.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExecutionServiceImpl implements ExecutionService {

    private final JobRepository jobRepository;

    @Override
    public void execute(Job job) {

        job.setStatus(JobStatus.RUNNING);
        jobRepository.save(job);

        try {

            System.out.println("Executing Job : " + job.getName());

            Thread.sleep(3000);

            job.setStatus(JobStatus.COMPLETED);
            job.setExecutedAt(LocalDateTime.now());

        } catch (Exception e) {

            job.setStatus(JobStatus.FAILED);

        }

        jobRepository.save(job);
    }
}