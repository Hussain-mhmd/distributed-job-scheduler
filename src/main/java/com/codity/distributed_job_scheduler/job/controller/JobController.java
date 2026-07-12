package com.codity.distributed_job_scheduler.job.controller;

import com.codity.distributed_job_scheduler.common.response.ApiResponse;
import com.codity.distributed_job_scheduler.job.dto.JobRequest;
import com.codity.distributed_job_scheduler.job.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(
        name = "Job APIs",
        description = "APIs for managing jobs"
)
@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    @Operation(summary = "Create Job")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createJob(@Valid @RequestBody JobRequest request) {

        return ApiResponse.success(
                "Job created successfully.",
                jobService.createJob(request));
    }
    @Operation(summary = "Get All Jobs")
    @GetMapping
    public ApiResponse<?> getJobs(
            @RequestParam(required = false) UUID queueId
    ) {

        if (queueId == null) {
            return ApiResponse.success(
                    "Jobs fetched successfully.",
                    jobService.getAll()
            );
        }

        return ApiResponse.success(
                "Jobs fetched successfully.",
                jobService.getByQueue(queueId)
        );
    }
    @Operation(summary = "Get Job By ID")
    @GetMapping("/{id}")
    public ApiResponse getJob(@PathVariable UUID id) {

        return ApiResponse.success(
                "Job fetched successfully.",
                jobService.getJob(id));
    }
    @Operation(summary = "Update Job")
    @PutMapping("/{id}")
    public ApiResponse updateJob(
            @PathVariable UUID id,
            @Valid @RequestBody JobRequest request) {

        return ApiResponse.success(
                "Job updated successfully.",
                jobService.updateJob(id, request));
    }
    @Operation(summary = "Delete Job")
    @DeleteMapping("/{id}")
    public ApiResponse deleteJob(@PathVariable UUID id) {

        jobService.deleteJob(id);

        return ApiResponse.success(
                "Job deleted successfully.",
                null);
    }
}