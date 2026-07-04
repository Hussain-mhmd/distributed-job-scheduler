package com.codity.distributed_job_scheduler.organization.controller;

import com.codity.distributed_job_scheduler.common.response.ApiResponse;
import com.codity.distributed_job_scheduler.organization.dto.OrganizationRequest;
import com.codity.distributed_job_scheduler.organization.dto.OrganizationResponse;
import com.codity.distributed_job_scheduler.organization.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<OrganizationResponse> createOrganization(
            @Valid @RequestBody OrganizationRequest request) {

        return ApiResponse.success(
                "Organization created successfully.",
                organizationService.createOrganization(request)
        );
    }
    @GetMapping
    public ApiResponse<List<OrganizationResponse>> getMyOrganizations() {

        return ApiResponse.success(
                "Organizations fetched successfully.",
                organizationService.getMyOrganizations()
        );
    }
    @GetMapping("/{id}")
    public ApiResponse<OrganizationResponse> getOrganization(
            @PathVariable UUID id) {

        return ApiResponse.success(
                "Organization fetched successfully.",
                organizationService.getOrganization(id)
        );
    }
    @PutMapping("/{id}")
    public ApiResponse<OrganizationResponse> updateOrganization(
            @PathVariable UUID id,
            @Valid @RequestBody OrganizationRequest request) {

        return ApiResponse.success(
                "Organization updated successfully.",
                organizationService.updateOrganization(id, request)
        );
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteOrganization(
            @PathVariable UUID id) {

        organizationService.deleteOrganization(id);

        return ApiResponse.success(
                "Organization deleted successfully.",
                null
        );
    }
}