package com.codity.distributed_job_scheduler.worker.service;

import com.codity.distributed_job_scheduler.worker.dto.WorkerResponse;

import java.util.List;
import java.util.UUID;

public interface WorkerService {

    void heartbeat(String workerName);

    void startJob(String workerName);

    void finishJob(String workerName);

    List<WorkerResponse> getAll();

    WorkerResponse getById(UUID id);

}