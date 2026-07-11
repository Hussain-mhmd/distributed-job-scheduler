package com.codity.distributed_job_scheduler.executionlog.service;

import com.codity.distributed_job_scheduler.executionlog.dto.ExecutionLogResponse;
import com.codity.distributed_job_scheduler.executionlog.entity.ExecutionLog;
import com.codity.distributed_job_scheduler.executionlog.mapper.ExecutionLogMapper;
import com.codity.distributed_job_scheduler.executionlog.repository.ExecutionLogRepository;
import com.codity.distributed_job_scheduler.jobexecution.entity.JobExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExecutionLogServiceImpl implements ExecutionLogService {

    private final ExecutionLogRepository repository;
    private final ExecutionLogMapper mapper;

    @Override
    public void info(JobExecution execution, String message) {

        repository.save(
                ExecutionLog.builder()
                        .execution(execution)
                        .logLevel("INFO")
                        .message(message)
                        .build()
        );
    }

    @Override
    public void error(JobExecution execution, String message) {

        repository.save(
                ExecutionLog.builder()
                        .execution(execution)
                        .logLevel("ERROR")
                        .message(message)
                        .build()
        );
    }

    @Override
    public List<ExecutionLogResponse> getLogs(UUID executionId) {

        return repository.findByExecutionIdOrderByCreatedAtAsc(executionId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}