package com.codity.distributed_job_scheduler.organization.service;

import com.codity.distributed_job_scheduler.auth.entity.User;
import com.codity.distributed_job_scheduler.common.util.SecurityUtil;
import com.codity.distributed_job_scheduler.exception.BadRequestException;
import com.codity.distributed_job_scheduler.exception.ResourceNotFoundException;
import com.codity.distributed_job_scheduler.organization.dto.OrganizationRequest;
import com.codity.distributed_job_scheduler.organization.dto.OrganizationResponse;
import com.codity.distributed_job_scheduler.organization.entity.Organization;
import com.codity.distributed_job_scheduler.organization.mapper.OrganizationMapper;
import com.codity.distributed_job_scheduler.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final SecurityUtil securityUtil;

    @Override
    public OrganizationResponse createOrganization(OrganizationRequest request) {

        User currentUser = securityUtil.getCurrentUser();

        if (organizationRepository.existsByNameAndOwnerId(
                request.getName(),
                currentUser.getId())) {

            throw new BadRequestException(
                    "Organization already exists.");
        }

        Organization organization = Organization.builder()
                .owner(currentUser)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        organizationRepository.save(organization);

        return organizationMapper.toResponse(organization);
    }

    @Override
    public List<OrganizationResponse> getMyOrganizations() {

        User currentUser = securityUtil.getCurrentUser();

        return organizationRepository.findByOwnerId(currentUser.getId())
                .stream()
                .map(organizationMapper::toResponse)
                .toList();
    }

    @Override
    public OrganizationResponse getOrganization(UUID id) {

        User currentUser = securityUtil.getCurrentUser();

        Organization organization = organizationRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found."));

        if (!organization.getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Access denied.");
        }

        return organizationMapper.toResponse(organization);
    }

    @Override
    public OrganizationResponse updateOrganization(
            UUID id,
            OrganizationRequest request) {

        User currentUser = securityUtil.getCurrentUser();

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found."));

        if (!organization.getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Access denied.");
        }

        organization.setName(request.getName());
        organization.setDescription(request.getDescription());

        organization.setUpdatedAt(java.time.LocalDateTime.now());

        organizationRepository.save(organization);

        return organizationMapper.toResponse(organization);
    }

    @Override
    public void deleteOrganization(UUID id) {

        User currentUser = securityUtil.getCurrentUser();

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found."));

        if (!organization.getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Access denied.");
        }

        organizationRepository.delete(organization);
    }
}