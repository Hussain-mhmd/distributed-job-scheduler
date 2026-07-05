package com.codity.distributed_job_scheduler.deadletter.controller;

import com.codity.distributed_job_scheduler.deadletter.dto.DeadLetterQueueResponse;
import com.codity.distributed_job_scheduler.deadletter.service.DeadLetterQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dead-letter")
@RequiredArgsConstructor
public class DeadLetterQueueController {

    private final DeadLetterQueueService deadLetterQueueService;

    @GetMapping
    public List<DeadLetterQueueResponse> getAll() {
        return deadLetterQueueService.getAll();
    }

    @GetMapping("/{id}")
    public DeadLetterQueueResponse getById(@PathVariable UUID id) {
        return deadLetterQueueService.getById(id);
    }
}