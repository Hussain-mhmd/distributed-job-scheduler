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
import com.codity.distributed_job_scheduler.auth.entity.User;
import com.codity.distributed_job_scheduler.common.util.SecurityUtil;
import com.codity.distributed_job_scheduler.exception.BadRequestException;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final QueueRepository queueRepository;
    private final JobMapper jobMapper;
    private final SecurityUtil securityUtil;

    @Override
    public JobResponse createJob(JobRequest request) {

        User currentUser = securityUtil.getCurrentUser();

        Queue queue = queueRepository.findById(request.getQueueId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Queue not found."));

        if (!queue.getProject().getOrganization().getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Access denied.");
        }

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

        User currentUser = securityUtil.getCurrentUser();

        return jobRepository
                .findByQueueProjectOrganizationOwnerId(currentUser.getId())
                .stream()
                .map(jobMapper::toResponse)
                .toList();
    }

    @Override
    public List<JobResponse> getByQueue(UUID queueId) {

        User currentUser = securityUtil.getCurrentUser();

        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Queue not found."));

        if (!queue.getProject().getOrganization().getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Access denied.");
        }

        return jobRepository.findByQueueId(queueId)
                .stream()
                .map(jobMapper::toResponse)
                .toList();
    }

    @Override
    public JobResponse getJob(UUID id) {

        User currentUser = securityUtil.getCurrentUser();

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found."));

        if (!job.getQueue().getProject().getOrganization().getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Access denied.");
        }

        return jobMapper.toResponse(job);
    }

    @Override
    public JobResponse updateJob(UUID id, JobRequest request) {

        User currentUser = securityUtil.getCurrentUser();

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found."));

        if (!job.getQueue().getProject().getOrganization().getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Access denied.");
        }

        Queue queue = queueRepository.findById(request.getQueueId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Queue not found."));

        if (!queue.getProject().getOrganization().getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Access denied.");
        }

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

        User currentUser = securityUtil.getCurrentUser();

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found."));

        if (!job.getQueue().getProject().getOrganization().getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Access denied.");
        }

        jobRepository.delete(job);
    }
}