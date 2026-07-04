package com.codity.distributed_job_scheduler.organization.service;

import com.codity.distributed_job_scheduler.organization.dto.OrganizationRequest;
import com.codity.distributed_job_scheduler.organization.dto.OrganizationResponse;

import java.util.List;
import java.util.UUID;

public interface OrganizationService {

    OrganizationResponse createOrganization(OrganizationRequest request);

    List<OrganizationResponse> getMyOrganizations();

    OrganizationResponse getOrganization(UUID id);

    OrganizationResponse updateOrganization(UUID id,
                                            OrganizationRequest request);

    void deleteOrganization(UUID id);
}