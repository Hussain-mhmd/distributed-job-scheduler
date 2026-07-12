package com.codity.distributed_job_scheduler.jobexecution.controller;

import com.codity.distributed_job_scheduler.common.response.ApiResponse;
import com.codity.distributed_job_scheduler.jobexecution.dto.JobExecutionResponse;
import com.codity.distributed_job_scheduler.jobexecution.service.JobExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Execution History APIs",
        description = "View execution history"
)
@RestController
@RequestMapping("/api/v1/job-executions")
@RequiredArgsConstructor
public class JobExecutionController {

    private final JobExecutionService service;

    @Operation(summary = "Get Execution History")

    @GetMapping
    public ApiResponse<List<JobExecutionResponse>> getExecutions(
            @RequestParam(required = false) UUID jobId) {

        if (jobId == null) {
            return ApiResponse.success(
                    "Execution history fetched successfully.",
                    service.getAll()
            );
        }

        return ApiResponse.success(
                "Execution history fetched successfully.",
                service.getByJob(jobId)
        );
    }
}