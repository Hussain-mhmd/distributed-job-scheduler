package com.codity.distributed_job_scheduler.metrics.controller;

import com.codity.distributed_job_scheduler.metrics.dto.MetricsResponse;
import com.codity.distributed_job_scheduler.metrics.service.MetricsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Dashboard APIs",
        description = "Dashboard metrics"
)
@RestController
@RequestMapping("/api/v1/metrics")
@RequiredArgsConstructor
public class MetricsController {

    private final MetricsService metricsService;

    @Operation(summary = "Get Dashboard Statistics")

    @GetMapping
    public MetricsResponse getMetrics() {
        return metricsService.getMetrics();
    }
}