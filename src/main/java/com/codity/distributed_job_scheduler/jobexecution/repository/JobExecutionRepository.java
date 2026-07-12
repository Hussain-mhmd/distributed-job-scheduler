package com.codity.distributed_job_scheduler.jobexecution.repository;

import com.codity.distributed_job_scheduler.jobexecution.entity.JobExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobExecutionRepository extends JpaRepository<JobExecution, UUID> {

    List<JobExecution> findByJobIdOrderByStartedAtDesc(UUID jobId);

    List<JobExecution> findAllByOrderByStartedAtDesc();

    List<JobExecution> findByJobQueueProjectOrganizationOwnerIdOrderByStartedAtDesc(
            UUID ownerId
    );

    List<JobExecution> findByJobIdAndJobQueueProjectOrganizationOwnerIdOrderByStartedAtDesc(
            UUID jobId,
            UUID ownerId
    );
}