package com.codity.distributed_job_scheduler.executionlog.repository;

import com.codity.distributed_job_scheduler.executionlog.entity.ExecutionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExecutionLogRepository extends JpaRepository<ExecutionLog, UUID> {

    List<ExecutionLog> findByExecutionIdOrderByCreatedAtAsc(UUID executionId);

}