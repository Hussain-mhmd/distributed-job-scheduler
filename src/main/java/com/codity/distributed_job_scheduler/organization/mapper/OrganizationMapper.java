package com.codity.distributed_job_scheduler.organization.mapper;

import com.codity.distributed_job_scheduler.organization.dto.OrganizationResponse;
import com.codity.distributed_job_scheduler.organization.entity.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    public OrganizationResponse toResponse(Organization organization) {

        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .description(organization.getDescription())
                .ownerName(organization.getOwner().getFullName())
                .ownerEmail(organization.getOwner().getEmail())
                .createdAt(organization.getCreatedAt())
                .build();
    }
}