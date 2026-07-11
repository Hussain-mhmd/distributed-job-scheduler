package com.codity.distributed_job_scheduler.job.service;

import com.codity.distributed_job_scheduler.job.dto.JobRequest;
import com.codity.distributed_job_scheduler.job.dto.JobResponse;

import java.util.List;
import java.util.UUID;

public interface JobService {

    JobResponse createJob(JobRequest request);

    List<JobResponse> getAll();

    List<JobResponse> getByQueue(UUID queueId);

    JobResponse getJob(UUID id);

    JobResponse updateJob(UUID id, JobRequest request);

    void deleteJob(UUID id);
}