package com.codity.distributed_job_scheduler.retry.service;

import com.codity.distributed_job_scheduler.common.enums.JobStatus;
import com.codity.distributed_job_scheduler.deadletter.service.DeadLetterQueueService;
import com.codity.distributed_job_scheduler.job.entity.Job;
import com.codity.distributed_job_scheduler.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetryServiceImpl implements RetryService {

    private final JobRepository jobRepository;
    private final DeadLetterQueueService deadLetterQueueService;

    @Override
    public void retry(Job job) {

        if (job.getRetryCount() < job.getMaxRetries()) {

            job.setRetryCount(job.getRetryCount() + 1);

            job.setStatus(JobStatus.PENDING);

            jobRepository.save(job);

            System.out.println(
                    "Retrying Job : "
                            + job.getName()
                            + " (Attempt "
                            + job.getRetryCount()
                            + ")"
            );

        } else {

            job.setStatus(JobStatus.FAILED);

            jobRepository.save(job);

            deadLetterQueueService.moveToDeadLetter(
                    job,
                    "Maximum retry attempts exceeded"
            );

            System.out.println(
                    "Job permanently failed : "
                            + job.getName()
            );

        }

    }
}