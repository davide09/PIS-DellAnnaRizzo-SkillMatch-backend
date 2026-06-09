
package com.skillmatch.project_service.service;

import com.skillmatch.project_service.dto.ProjectRequest;
import com.skillmatch.project_service.dto.ProjectResponse;
import com.skillmatch.project_service.model.Project;
import com.skillmatch.project_service.model.ProjectStatus;
import com.skillmatch.project_service.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectResponse create(ProjectRequest req) {
        Project project = Project.builder()
                .companyId(req.getCompanyId())
                .title(req.getTitle())
                .description(req.getDescription())
                .budget(req.getBudget())
                .requiredSkills(req.getRequiredSkills())
                .experienceLevel(req.getExperienceLevel())
                .status(ProjectStatus.OPEN)
                .build();

        Project saved = projectRepository.save(project);
        return toResponse(saved);
    }

    public ProjectResponse getById(Long id) {
        Project p = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return toResponse(p);
    }

    public List<ProjectResponse> getAll() {
        return projectRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProjectResponse> getByCompany(Long companyId) {
        return projectRepository.findByCompanyId(companyId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<Project> findByCompany(Long companyId) {
        return projectRepository.findByCompanyId(companyId);
    }

    public ProjectResponse update(Long id, ProjectRequest req) {
        Project p = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        p.setTitle(req.getTitle());
        p.setDescription(req.getDescription());
        p.setBudget(req.getBudget());
        p.setRequiredSkills(req.getRequiredSkills());
        p.setExperienceLevel(req.getExperienceLevel());

        Project saved = projectRepository.save(p);
        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(id);
    }

    private ProjectResponse toResponse(Project p) {
        return ProjectResponse.builder()
                .id(p.getId())
                .companyId(p.getCompanyId())
                .title(p.getTitle())
                .description(p.getDescription())
                .budget(p.getBudget())
                .requiredSkills(p.getRequiredSkills())
                .experienceLevel(p.getExperienceLevel())
                .status(p.getStatus().name())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}