package com.codity.distributed_job_scheduler.project.controller;

import com.codity.distributed_job_scheduler.common.response.ApiResponse;
import com.codity.distributed_job_scheduler.project.dto.ProjectRequest;
import com.codity.distributed_job_scheduler.project.dto.ProjectResponse;
import com.codity.distributed_job_scheduler.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProjectResponse> createProject(
            @Valid @RequestBody ProjectRequest request) {

        return ApiResponse.success(
                "Project created successfully.",
                projectService.createProject(request)
        );
    }

    @GetMapping
    public ApiResponse<List<ProjectResponse>> getProjects(
            @RequestParam(required = false) UUID organizationId) {

        if (organizationId == null) {
            return ApiResponse.success(
                    "Projects fetched successfully.",
                    projectService.getAll()
            );
        }

        return ApiResponse.success(
                "Projects fetched successfully.",
                projectService.getByOrganization(organizationId)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<ProjectResponse> getProject(
            @PathVariable UUID id) {

        return ApiResponse.success(
                "Project fetched successfully.",
                projectService.getProject(id)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<ProjectResponse> updateProject(
            @PathVariable UUID id,
            @Valid @RequestBody ProjectRequest request) {

        return ApiResponse.success(
                "Project updated successfully.",
                projectService.updateProject(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProject(
            @PathVariable UUID id) {

        projectService.deleteProject(id);

        return ApiResponse.success(
                "Project deleted successfully.",
                null
        );
    }
}