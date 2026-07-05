package com.codity.distributed_job_scheduler.metrics.controller;

import com.codity.distributed_job_scheduler.metrics.dto.MetricsResponse;
import com.codity.distributed_job_scheduler.metrics.service.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/metrics")
@RequiredArgsConstructor
public class MetricsController {

    private final MetricsService metricsService;

    @GetMapping
    public MetricsResponse getMetrics() {
        return metricsService.getMetrics();
    }
}