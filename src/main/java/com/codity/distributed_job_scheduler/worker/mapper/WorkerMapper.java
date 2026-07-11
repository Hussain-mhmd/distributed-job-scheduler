package com.codity.distributed_job_scheduler.worker.mapper;

import com.codity.distributed_job_scheduler.worker.dto.WorkerResponse;
import com.codity.distributed_job_scheduler.worker.entity.Worker;
import org.springframework.stereotype.Component;

@Component
public class WorkerMapper {

    public WorkerResponse toResponse(Worker worker){

        return WorkerResponse.builder()
                .id(worker.getId())
                .workerName(worker.getWorkerName())
                .hostName(worker.getHostName())
                .workerStatus(worker.getWorkerStatus())
                .activeJobs(worker.getActiveJobs())
                .lastHeartbeat(worker.getLastHeartbeat())
                .build();
    }

}