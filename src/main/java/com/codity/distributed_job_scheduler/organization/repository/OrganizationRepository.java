package com.codity.distributed_job_scheduler.organization.repository;

import com.codity.distributed_job_scheduler.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrganizationRepository
        extends JpaRepository<Organization, UUID> {

    List<Organization> findByOwnerId(UUID ownerId);

    boolean existsByNameAndOwnerId(String name, UUID ownerId);
}