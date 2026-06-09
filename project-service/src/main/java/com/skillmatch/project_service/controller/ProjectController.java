package com.skillmatch.project_service.controller;

import com.skillmatch.project_service.dto.ProjectRequest;
import com.skillmatch.project_service.dto.ProjectResponse;
import com.skillmatch.project_service.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService service;

    // =======================================================
    // INTERNAL ENDPOINTS per matching-service
    // =======================================================

    @GetMapping("/internal/{id}")
    public ProjectResponse internalGetById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/internal")
    public List<ProjectResponse> internalGetAll() {
        return service.getAll();
    }

    // =======================================================
    // Endpoint per utenti reali
    // =======================================================

    @GetMapping
    public List<ProjectResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ProjectResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ProjectResponse create(@RequestBody ProjectRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public ProjectResponse update(@PathVariable Long id, @RequestBody ProjectRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // l ho modificato come dto,vedere se cambiare frontend
    @GetMapping("/company/{companyId}")
    public List<ProjectResponse> getProjectsByCompany(@PathVariable Long companyId) {
        return service.getByCompany(companyId);
    }
}
