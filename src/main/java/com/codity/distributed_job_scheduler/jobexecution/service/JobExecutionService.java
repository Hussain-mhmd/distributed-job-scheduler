package com.codity.distributed_job_scheduler.jobexecution.service;

import com.codity.distributed_job_scheduler.job.entity.Job;
import com.codity.distributed_job_scheduler.jobexecution.dto.JobExecutionResponse;
import com.codity.distributed_job_scheduler.jobexecution.entity.JobExecution;

import java.util.List;
import java.util.UUID;

public interface JobExecutionService {

    JobExecution startExecution(Job job);

    void completeExecution(JobExecution execution);

    void failExecution(JobExecution execution, String error);

    List<JobExecutionResponse> getAll();

    List<JobExecutionResponse> getByJob(UUID jobId);
}