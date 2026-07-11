package com.codity.distributed_job_scheduler.project.service;

import com.codity.distributed_job_scheduler.project.dto.ProjectRequest;
import com.codity.distributed_job_scheduler.project.dto.ProjectResponse;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    ProjectResponse createProject(ProjectRequest request);

    List<ProjectResponse> getAll();

    List<ProjectResponse> getByOrganization(UUID organizationId);

    ProjectResponse getProject(UUID id);

    ProjectResponse updateProject(UUID id,
                                  ProjectRequest request);

    void deleteProject(UUID id);
}