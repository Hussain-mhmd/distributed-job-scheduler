package com.codity.distributed_job_scheduler.jobexecution.mapper;

import com.codity.distributed_job_scheduler.jobexecution.dto.JobExecutionResponse;
import com.codity.distributed_job_scheduler.jobexecution.entity.JobExecution;
import org.springframework.stereotype.Component;

@Component
public class JobExecutionMapper {

    public JobExecutionResponse toResponse(JobExecution execution) {

        return JobExecutionResponse.builder()
                .id(execution.getId())
                .jobId(execution.getJob().getId())
                .jobName(execution.getJob().getName())
                .workerName(execution.getWorkerName())
                .attemptNumber(execution.getAttemptNumber())
                .executionStatus(execution.getExecutionStatus())
                .startedAt(execution.getStartedAt())
                .completedAt(execution.getCompletedAt())
                .executionTimeMs(execution.getExecutionTimeMs())
                .errorMessage(execution.getErrorMessage())
                .build();
    }
}