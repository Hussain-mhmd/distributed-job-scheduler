package com.codity.distributed_job_scheduler.project.mapper;

import com.codity.distributed_job_scheduler.project.dto.ProjectResponse;
import com.codity.distributed_job_scheduler.project.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectResponse toResponse(Project project) {

        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .organizationId(project.getOrganization().getId())
                .organizationName(project.getOrganization().getName())
                .createdAt(project.getCreatedAt())
                .build();
    }
}