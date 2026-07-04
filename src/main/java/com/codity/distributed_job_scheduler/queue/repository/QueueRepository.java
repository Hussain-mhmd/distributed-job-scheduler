package com.codity.distributed_job_scheduler.queue.repository;

import com.codity.distributed_job_scheduler.queue.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QueueRepository extends JpaRepository<Queue, UUID> {

    List<Queue> findByProjectId(UUID projectId);

    boolean existsByNameAndProjectId(
            String name,
            UUID projectId
    );
}