package com.codity.distributed_job_scheduler.deadletter.service;

import com.codity.distributed_job_scheduler.deadletter.dto.DeadLetterQueueResponse;
import com.codity.distributed_job_scheduler.deadletter.entity.DeadLetterQueue;
import com.codity.distributed_job_scheduler.deadletter.repository.DeadLetterQueueRepository;
import com.codity.distributed_job_scheduler.job.entity.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
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
    @Override
    public List<DeadLetterQueueResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public DeadLetterQueueResponse getById(UUID id) {

        return mapToResponse(
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Dead Letter not found"))
        );
    }

    private DeadLetterQueueResponse mapToResponse(DeadLetterQueue deadLetter) {

        return DeadLetterQueueResponse.builder()
                .id(deadLetter.getId())
                .jobId(deadLetter.getJob().getId())
                .jobName(deadLetter.getJob().getName())
                .failureReason(deadLetter.getFailureReason())
                .failedAt(deadLetter.getFailedAt())
                .createdAt(deadLetter.getCreatedAt())
                .build();
    }
}