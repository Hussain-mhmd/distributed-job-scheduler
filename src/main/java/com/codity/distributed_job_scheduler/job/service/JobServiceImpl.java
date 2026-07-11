package com.codity.distributed_job_scheduler.job.service;

import com.codity.distributed_job_scheduler.exception.ResourceNotFoundException;
import com.codity.distributed_job_scheduler.job.dto.JobRequest;
import com.codity.distributed_job_scheduler.job.dto.JobResponse;
import com.codity.distributed_job_scheduler.job.entity.Job;
import com.codity.distributed_job_scheduler.job.mapper.JobMapper;
import com.codity.distributed_job_scheduler.job.repository.JobRepository;
import com.codity.distributed_job_scheduler.queue.entity.Queue;
import com.codity.distributed_job_scheduler.queue.repository.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final QueueRepository queueRepository;
    private final JobMapper jobMapper;

    @Override
    public JobResponse createJob(JobRequest request) {

        Queue queue = queueRepository.findById(request.getQueueId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Queue not found."));

        Job job = Job.builder()
                .queue(queue)
                .name(request.getName())
                .payload(request.getPayload())
                .priority(request.getPriority())
                .maxRetries(request.getMaxRetries())
                .scheduledAt(request.getScheduledAt())
                .build();

        jobRepository.save(job);

        return jobMapper.toResponse(job);
    }

    @Override
    public List<JobResponse> getAll() {

        return jobRepository.findAll()
                .stream()
                .map(jobMapper::toResponse)
                .toList();
    }

    @Override
    public List<JobResponse> getByQueue(UUID queueId) {

        return jobRepository.findByQueueId(queueId)
                .stream()
                .map(jobMapper::toResponse)
                .toList();
    }

    @Override
    public JobResponse getJob(UUID id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found."));

        return jobMapper.toResponse(job);
    }

    @Override
    public JobResponse updateJob(UUID id, JobRequest request) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found."));

        Queue queue = queueRepository.findById(request.getQueueId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Queue not found."));

        job.setQueue(queue);
        job.setName(request.getName());
        job.setPayload(request.getPayload());
        job.setPriority(request.getPriority());
        job.setMaxRetries(request.getMaxRetries());
        job.setScheduledAt(request.getScheduledAt());
        job.setUpdatedAt(LocalDateTime.now());

        jobRepository.save(job);

        return jobMapper.toResponse(job);
    }

    @Override
    public void deleteJob(UUID id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found."));

        jobRepository.delete(job);
    }
}