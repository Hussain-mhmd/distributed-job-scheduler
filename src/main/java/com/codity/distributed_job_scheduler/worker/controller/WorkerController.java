package com.codity.distributed_job_scheduler.worker.controller;

import com.codity.distributed_job_scheduler.common.response.ApiResponse;
import com.codity.distributed_job_scheduler.worker.dto.WorkerResponse;
import com.codity.distributed_job_scheduler.worker.service.WorkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Worker APIs",
        description = "Monitor worker status"
)
@RestController
@RequestMapping("/api/v1/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    @Operation(summary = "Get All Workers")

    @GetMapping
    public ApiResponse<List<WorkerResponse>> getWorkers() {

        return ApiResponse.success(
                "Workers fetched successfully.",
                workerService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<WorkerResponse> getWorker(
            @PathVariable UUID id) {

        return ApiResponse.success(
                "Worker fetched successfully.",
                workerService.getById(id)
        );
    }
}