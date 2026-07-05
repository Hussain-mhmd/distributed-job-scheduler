package com.codity.distributed_job_scheduler.deadletter.service;

import com.codity.distributed_job_scheduler.job.entity.Job;
import com.codity.distributed_job_scheduler.deadletter.dto.DeadLetterQueueResponse;

import java.util.List;
import java.util.UUID;

public interface DeadLetterQueueService {

    void moveToDeadLetter(Job job, String reason);
    List<DeadLetterQueueResponse> getAll();

    DeadLetterQueueResponse getById(UUID id);

}