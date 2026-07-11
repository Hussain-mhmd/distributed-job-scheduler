package com.codity.distributed_job_scheduler.worker.service;

import com.codity.distributed_job_scheduler.exception.ResourceNotFoundException;
import com.codity.distributed_job_scheduler.worker.dto.WorkerResponse;
import com.codity.distributed_job_scheduler.worker.entity.Worker;
import com.codity.distributed_job_scheduler.worker.mapper.WorkerMapper;
import com.codity.distributed_job_scheduler.worker.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository repository;
    private final WorkerMapper mapper;

    @Override
    public void heartbeat(String workerName) {

        Worker worker = repository.findByWorkerName(workerName)
                .orElseGet(() -> Worker.builder()
                        .workerName(workerName)
                        .hostName(getHostName())
                        .build());

        worker.setLastHeartbeat(LocalDateTime.now());
        worker.setWorkerStatus("ACTIVE");

        repository.save(worker);
    }

    @Override
    public void startJob(String workerName) {

        Worker worker = repository.findByWorkerName(workerName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Worker not found."));

        worker.setActiveJobs(worker.getActiveJobs() + 1);
        worker.setLastHeartbeat(LocalDateTime.now());

        repository.save(worker);
    }

    @Override
    public void finishJob(String workerName) {

        Worker worker = repository.findByWorkerName(workerName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Worker not found."));

        worker.setActiveJobs(
                Math.max(0, worker.getActiveJobs() - 1));

        worker.setLastHeartbeat(LocalDateTime.now());

        repository.save(worker);
    }

    @Override
    public List<WorkerResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public WorkerResponse getById(UUID id) {

        Worker worker = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Worker not found."));

        return mapper.toResponse(worker);
    }

    private String getHostName() {

        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "localhost";
        }
    }
}