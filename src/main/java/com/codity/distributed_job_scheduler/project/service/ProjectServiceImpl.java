package com.codity.distributed_job_scheduler.project.service;

import com.codity.distributed_job_scheduler.auth.entity.User;
import com.codity.distributed_job_scheduler.common.util.SecurityUtil;
import com.codity.distributed_job_scheduler.exception.BadRequestException;
import com.codity.distributed_job_scheduler.exception.ResourceNotFoundException;
import com.codity.distributed_job_scheduler.organization.entity.Organization;
import com.codity.distributed_job_scheduler.organization.repository.OrganizationRepository;
import com.codity.distributed_job_scheduler.project.dto.ProjectRequest;
import com.codity.distributed_job_scheduler.project.dto.ProjectResponse;
import com.codity.distributed_job_scheduler.project.entity.Project;
import com.codity.distributed_job_scheduler.project.mapper.ProjectMapper;
import com.codity.distributed_job_scheduler.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final OrganizationRepository organizationRepository;
    private final ProjectMapper projectMapper;
    private final SecurityUtil securityUtil;

    @Override
    public ProjectResponse createProject(ProjectRequest request) {

        User currentUser = securityUtil.getCurrentUser();

        Organization organization = organizationRepository
                .findById(request.getOrganizationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found."));

        if (!organization.getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Access denied.");
        }

        if (projectRepository.existsByNameAndOrganizationId(
                request.getName(),
                organization.getId())) {

            throw new BadRequestException("Project already exists.");
        }

        Project project = Project.builder()
                .organization(organization)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        projectRepository.save(project);

        return projectMapper.toResponse(project);
    }

    @Override
    public List<ProjectResponse> getProjects(UUID organizationId) {

        return projectRepository.findByOrganizationId(organizationId)
                .stream()
                .map(projectMapper::toResponse)
                .toList();
    }

    @Override
    public ProjectResponse getProject(UUID id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found."));

        return projectMapper.toResponse(project);
    }

    @Override
    public ProjectResponse updateProject(UUID id,
                                         ProjectRequest request) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found."));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setUpdatedAt(LocalDateTime.now());

        projectRepository.save(project);

        return projectMapper.toResponse(project);
    }

    @Override
    public void deleteProject(UUID id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found."));

        projectRepository.delete(project);
    }
}