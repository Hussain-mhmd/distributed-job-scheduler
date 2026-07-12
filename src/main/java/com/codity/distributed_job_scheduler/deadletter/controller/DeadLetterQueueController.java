package com.codity.distributed_job_scheduler.deadletter.controller;

import com.codity.distributed_job_scheduler.deadletter.dto.DeadLetterQueueResponse;
import com.codity.distributed_job_scheduler.deadletter.service.DeadLetterQueueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Dead Letter Queue APIs",
        description = "View failed jobs"
)
@RestController
@RequestMapping("/api/v1/dead-letter")
@RequiredArgsConstructor
public class DeadLetterQueueController {

    private final DeadLetterQueueService deadLetterQueueService;

    @Operation(summary = "Get All Failed Jobs")

    @GetMapping
    public List<DeadLetterQueueResponse> getAll() {
        return deadLetterQueueService.getAll();
    }

    @Operation(summary = "Get Failed Job By ID")

    @GetMapping("/{id}")
    public DeadLetterQueueResponse getById(@PathVariable UUID id) {
        return deadLetterQueueService.getById(id);
    }
}