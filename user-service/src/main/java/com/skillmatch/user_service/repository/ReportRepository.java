
package com.skillmatch.user_service.repository;

import com.skillmatch.user_service.model.Report;
import com.skillmatch.user_service.model.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByReportedUserId(Long userId);

    List<Report> findByStatus(ReportStatus status);
}