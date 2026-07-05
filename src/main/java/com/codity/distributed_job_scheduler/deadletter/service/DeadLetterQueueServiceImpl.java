package com.codity.distributed_job_scheduler.deadletter.service;

import com.codity.distributed_job_scheduler.deadletter.entity.DeadLetterQueue;
import com.codity.distributed_job_scheduler.deadletter.repository.DeadLetterQueueRepository;
import com.codity.distributed_job_scheduler.job.entity.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeadLetterQueueServiceImpl
        implements DeadLetterQueueService {

    private final DeadLetterQueueRepository repository;

    @Override
    public void moveToDeadLetter(Job job, String reason) {

        DeadLetterQueue deadLetter =
                DeadLetterQueue.builder()
                        .job(job)
                        .failureReason(reason)
                        .failedAt(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .build();

        repository.save(deadLetter);

        System.out.println(
                "Moved to Dead Letter Queue : "
                        + job.getName()
        );
    }
}