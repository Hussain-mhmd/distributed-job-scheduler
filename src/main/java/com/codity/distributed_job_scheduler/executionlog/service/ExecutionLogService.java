package com.codity.distributed_job_scheduler.executionlog.service;

import com.codity.distributed_job_scheduler.executionlog.dto.ExecutionLogResponse;
import com.codity.distributed_job_scheduler.jobexecution.entity.JobExecution;

import java.util.List;
import java.util.UUID;

public interface ExecutionLogService {

    void info(JobExecution execution, String message);

    void error(JobExecution execution, String message);

    List<ExecutionLogResponse> getLogs(UUID executionId);

}