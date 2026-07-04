package com.codity.distributed_job_scheduler.queue.mapper;

import com.codity.distributed_job_scheduler.queue.dto.QueueResponse;
import com.codity.distributed_job_scheduler.queue.entity.Queue;
import org.springframework.stereotype.Component;

@Component
public class QueueMapper {

    public QueueResponse toResponse(Queue queue) {

        return QueueResponse.builder()
                .id(queue.getId())
                .name(queue.getName())
                .description(queue.getDescription())
                .projectId(queue.getProject().getId())
                .projectName(queue.getProject().getName())
                .createdAt(queue.getCreatedAt())
                .build();
    }
}