package com.codity.distributed_job_scheduler.worker.controller;

import com.codity.distributed_job_scheduler.common.response.ApiResponse;
import com.codity.distributed_job_scheduler.worker.dto.WorkerResponse;
import com.codity.distributed_job_scheduler.worker.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

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