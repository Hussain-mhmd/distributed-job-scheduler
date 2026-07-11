package com.codity.distributed_job_scheduler.executionlog.mapper;

import com.codity.distributed_job_scheduler.executionlog.dto.ExecutionLogResponse;
import com.codity.distributed_job_scheduler.executionlog.entity.ExecutionLog;
import org.springframework.stereotype.Component;

@Component
public class ExecutionLogMapper {

    public ExecutionLogResponse toResponse(ExecutionLog log){

        return ExecutionLogResponse.builder()
                .logLevel(log.getLogLevel())
                .message(log.getMessage())
                .createdAt(log.getCreatedAt())
                .build();
    }
}