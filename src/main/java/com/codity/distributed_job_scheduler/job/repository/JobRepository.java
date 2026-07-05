package com.codity.distributed_job_scheduler.job.repository;

import com.codity.distributed_job_scheduler.common.enums.JobStatus;
import com.codity.distributed_job_scheduler.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import com.codity.distributed_job_scheduler.common.enums.JobStatus;
import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {

    List<Job> findByQueueId(UUID queueId);

    List<Job> findByStatus(JobStatus status);
    List<Job> findByStatusAndScheduledAtLessThanEqual(
            JobStatus status,
            LocalDateTime scheduledAt);
}