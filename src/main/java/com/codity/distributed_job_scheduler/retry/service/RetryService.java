package com.codity.distributed_job_scheduler.retry.service;

import com.codity.distributed_job_scheduler.job.entity.Job;

public interface RetryService {

    void retry(Job job);

}