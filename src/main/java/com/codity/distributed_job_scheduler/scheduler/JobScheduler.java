package com.codity.distributed_job_scheduler.scheduler;

import com.codity.distributed_job_scheduler.common.enums.JobStatus;
import com.codity.distributed_job_scheduler.execution.service.ExecutionService;
import com.codity.distributed_job_scheduler.job.entity.Job;
import com.codity.distributed_job_scheduler.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JobScheduler {

    private final JobRepository jobRepository;
    private final ExecutionService executionService;

    @Scheduled(fixedDelay = 5000)
    public void scheduleJobs() {

        List<Job> jobs =
                jobRepository.findByStatusAndScheduledAtLessThanEqual(
                        JobStatus.PENDING,
                        LocalDateTime.now());

        for (Job job : jobs) {

            executionService.execute(job);

        }

    }
}