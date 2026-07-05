package com.codity.distributed_job_scheduler.deadletter.service;

import com.codity.distributed_job_scheduler.job.entity.Job;

public interface DeadLetterQueueService {

    void moveToDeadLetter(Job job, String reason);

}