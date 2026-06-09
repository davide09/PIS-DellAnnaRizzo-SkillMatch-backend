
package com.skillmatch.project_service.repository;

import com.skillmatch.project_service.model.Project;
import com.skillmatch.project_service.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByCompanyId(Long companyId);

    List<Project> findByStatus(ProjectStatus status);
}