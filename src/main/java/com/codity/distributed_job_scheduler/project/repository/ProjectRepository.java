package com.codity.distributed_job_scheduler.project.repository;

import com.codity.distributed_job_scheduler.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findByOrganizationId(UUID organizationId);

    boolean existsByNameAndOrganizationId(
            String name,
            UUID organizationId
    );
    List<Project> findByOrganizationOwnerId(UUID ownerId);

    long countByOrganizationOwnerId(UUID ownerId);
}