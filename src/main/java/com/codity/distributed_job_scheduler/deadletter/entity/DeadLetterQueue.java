package com.codity.distributed_job_scheduler.deadletter.entity;

import com.codity.distributed_job_scheduler.job.entity.Job;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "dead_letter_queue")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeadLetterQueue {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "job_id", nullable = false, unique = true)
    private Job job;

    @Column(nullable = false)
    private String failureReason;

    @Column(nullable = false)
    private LocalDateTime failedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}