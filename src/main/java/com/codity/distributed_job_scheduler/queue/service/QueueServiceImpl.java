package com.codity.distributed_job_scheduler.queue.service;

import com.codity.distributed_job_scheduler.auth.entity.User;
import com.codity.distributed_job_scheduler.common.util.SecurityUtil;
import com.codity.distributed_job_scheduler.exception.BadRequestException;
import com.codity.distributed_job_scheduler.exception.ResourceNotFoundException;
import com.codity.distributed_job_scheduler.project.entity.Project;
import com.codity.distributed_job_scheduler.project.repository.ProjectRepository;
import com.codity.distributed_job_scheduler.queue.dto.QueueRequest;
import com.codity.distributed_job_scheduler.queue.dto.QueueResponse;
import com.codity.distributed_job_scheduler.queue.entity.Queue;
import com.codity.distributed_job_scheduler.queue.mapper.QueueMapper;
import com.codity.distributed_job_scheduler.queue.repository.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepository;
    private final ProjectRepository projectRepository;
    private final QueueMapper queueMapper;
    private final SecurityUtil securityUtil;

    @Override
    public QueueResponse createQueue(QueueRequest request) {

        User currentUser = securityUtil.getCurrentUser();

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found."));

        if (!project.getOrganization().getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Access denied.");
        }

        if (queueRepository.existsByNameAndProjectId(
                request.getName(),
                project.getId())) {

            throw new BadRequestException("Queue already exists.");
        }

        Queue queue = Queue.builder()
                .project(project)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        queueRepository.save(queue);

        return queueMapper.toResponse(queue);
    }

    @Override
    public List<QueueResponse> getAll() {

        return queueRepository.findAll()
                .stream()
                .map(queueMapper::toResponse)
                .toList();
    }

    @Override
    public List<QueueResponse> getByProject(UUID projectId) {

        return queueRepository.findByProjectId(projectId)
                .stream()
                .map(queueMapper::toResponse)
                .toList();
    }

    @Override
    public QueueResponse getQueue(UUID id) {

        Queue queue = queueRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Queue not found."));

        return queueMapper.toResponse(queue);
    }

    @Override
    public QueueResponse updateQueue(UUID id, QueueRequest request) {

        Queue queue = queueRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Queue not found."));

        queue.setName(request.getName());
        queue.setDescription(request.getDescription());
        queue.setUpdatedAt(LocalDateTime.now());

        queueRepository.save(queue);

        return queueMapper.toResponse(queue);
    }

    @Override
    public void deleteQueue(UUID id) {

        Queue queue = queueRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Queue not found."));

        queueRepository.delete(queue);
    }
}