package com.codity.distributed_job_scheduler.queue.controller;

import com.codity.distributed_job_scheduler.common.response.ApiResponse;
import com.codity.distributed_job_scheduler.queue.dto.QueueRequest;
import com.codity.distributed_job_scheduler.queue.dto.QueueResponse;
import com.codity.distributed_job_scheduler.queue.service.QueueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/queues")
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<QueueResponse> createQueue(
            @Valid @RequestBody QueueRequest request) {

        return ApiResponse.success(
                "Queue created successfully.",
                queueService.createQueue(request)
        );
    }

    @GetMapping
    public ApiResponse<List<QueueResponse>> getQueues(
            @RequestParam(required = false) UUID projectId) {

        if (projectId == null) {
            return ApiResponse.success(
                    "Queues fetched successfully.",
                    queueService.getAll()
            );
        }

        return ApiResponse.success(
                "Queues fetched successfully.",
                queueService.getByProject(projectId)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<QueueResponse> getQueue(
            @PathVariable UUID id) {

        return ApiResponse.success(
                "Queue fetched successfully.",
                queueService.getQueue(id)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<QueueResponse> updateQueue(
            @PathVariable UUID id,
            @Valid @RequestBody QueueRequest request) {

        return ApiResponse.success(
                "Queue updated successfully.",
                queueService.updateQueue(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteQueue(
            @PathVariable UUID id) {

        queueService.deleteQueue(id);

        return ApiResponse.success(
                "Queue deleted successfully.",
                null
        );
    }
}