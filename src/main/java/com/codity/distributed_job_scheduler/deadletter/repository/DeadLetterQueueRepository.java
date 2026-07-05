package com.codity.distributed_job_scheduler.deadletter.repository;

import com.codity.distributed_job_scheduler.deadletter.entity.DeadLetterQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeadLetterQueueRepository
        extends JpaRepository<DeadLetterQueue, UUID> {
    long count();
}