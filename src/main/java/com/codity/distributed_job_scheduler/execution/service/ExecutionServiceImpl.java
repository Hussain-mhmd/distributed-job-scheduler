package com.codity.distributed_job_scheduler.execution.service;

import com.codity.distributed_job_scheduler.common.enums.JobStatus;
import com.codity.distributed_job_scheduler.executionlog.service.ExecutionLogService;
import com.codity.distributed_job_scheduler.job.entity.Job;
import com.codity.distributed_job_scheduler.job.repository.JobRepository;
import com.codity.distributed_job_scheduler.jobexecution.entity.JobExecution;
import com.codity.distributed_job_scheduler.jobexecution.service.JobExecutionService;
import com.codity.distributed_job_scheduler.retry.service.RetryService;
import com.codity.distributed_job_scheduler.worker.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExecutionServiceImpl implements ExecutionService {

    private final JobRepository jobRepository;
    private final RetryService retryService;
    private final JobExecutionService jobExecutionService;
    private final WorkerService workerService;
    private final ExecutionLogService executionLogService;

    @Async("jobExecutor")
    @Override
    public void execute(Job job) {

        String workerName = Thread.currentThread().getName();

        workerService.heartbeat(workerName);
        workerService.startJob(workerName);

        JobExecution execution = jobExecutionService.startExecution(job);

        try {

            executionLogService.info(
                    execution,
                    "Job execution started."
            );

            job.setStatus(JobStatus.RUNNING);
            jobRepository.save(job);

            System.out.println(
                    workerName + " -> Executing : " + job.getName()
            );

            Thread.sleep(5000);

            // Simulate random failure
            if (Math.random() < 0.4) {
                throw new RuntimeException("Simulated Failure");
            }

            job.setStatus(JobStatus.COMPLETED);
            job.setExecutedAt(LocalDateTime.now());

            jobRepository.save(job);

            executionLogService.info(
                    execution,
                    "Job completed successfully."
            );

            jobExecutionService.completeExecution(execution);

        } catch (Exception e) {

            job.setStatus(JobStatus.FAILED);
            jobRepository.save(job);

            executionLogService.error(
                    execution,
                    e.getMessage()
            );

            jobExecutionService.failExecution(
                    execution,
                    e.getMessage()
            );

            retryService.retry(job);

        } finally {

            workerService.finishJob(workerName);

        }
    }
}