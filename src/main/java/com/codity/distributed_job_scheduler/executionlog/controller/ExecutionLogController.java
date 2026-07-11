package com.codity.distributed_job_scheduler.executionlog.controller;

import com.codity.distributed_job_scheduler.common.response.ApiResponse;
import com.codity.distributed_job_scheduler.executionlog.dto.ExecutionLogResponse;
import com.codity.distributed_job_scheduler.executionlog.service.ExecutionLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/execution-logs")
@RequiredArgsConstructor
public class ExecutionLogController {

    private final ExecutionLogService service;

    @GetMapping
    public ApiResponse<List<ExecutionLogResponse>> getLogs(
            @RequestParam UUID executionId) {

        return ApiResponse.success(
                "Execution logs fetched successfully.",
                service.getLogs(executionId)
        );
    }
}