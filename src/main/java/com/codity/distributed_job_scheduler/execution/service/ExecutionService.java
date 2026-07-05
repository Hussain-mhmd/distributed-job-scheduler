package com.codity.distributed_job_scheduler.execution.service;

import com.codity.distributed_job_scheduler.job.entity.Job;

public interface ExecutionService {

    void execute(Job job);

}