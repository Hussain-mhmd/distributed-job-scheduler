package com.codity.distributed_job_scheduler.queue.service;

import com.codity.distributed_job_scheduler.queue.dto.QueueRequest;
import com.codity.distributed_job_scheduler.queue.dto.QueueResponse;

import java.util.List;
import java.util.UUID;

public interface QueueService {

    QueueResponse createQueue(QueueRequest request);

    List<QueueResponse> getQueues(UUID projectId);

    QueueResponse getQueue(UUID id);

    QueueResponse updateQueue(UUID id, QueueRequest request);

    void deleteQueue(UUID id);
}