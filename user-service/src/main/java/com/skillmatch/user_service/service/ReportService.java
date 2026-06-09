package com.skillmatch.user_service.service;

import com.skillmatch.user_service.dto.ReportDTO;
import com.skillmatch.user_service.model.Report;
import com.skillmatch.user_service.model.ReportStatus;
import com.skillmatch.user_service.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository repo;

    public ReportDTO create(Long reporterId, Long reportedUserId, String description) {
        Report r = new Report();
        r.setReporterId(reporterId);
        r.setReportedUserId(reportedUserId);
        r.setDescription(description);
        r.setStatus(ReportStatus.OPEN);
        return toDTO(repo.save(r));
    }

    public List<ReportDTO> getAll() {
        return repo.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ReportDTO> getOpenReports() {
        return repo.findByStatus(ReportStatus.OPEN).stream()
                .map(this::toDTO)
                .toList();
    }

    public ReportDTO getById(Long id) {
        return repo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Report non trovato."));
    }

    public ReportDTO closeReport(Long id) {
        Report r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Report non trovato."));
        r.setStatus(ReportStatus.CLOSED);
        return toDTO(repo.save(r));
    }

    public List<ReportDTO> getReportsForUser(Long userId) {
        return repo.findByReportedUserId(userId).stream()
                .map(this::toDTO)
                .toList();
    }


    public Optional<ReportDTO> getLatestReportForUser(Long userId) {
        return repo.findByReportedUserId(userId)
                .stream()
                .filter(r -> r.getStatus() == ReportStatus.OPEN)
                .reduce((first, second) -> second)
                .map(this::toDTO);
    }

    public void closeAllForUser(Long userId) {
        List<Report> reports = repo.findByReportedUserId(userId);
        for (Report r : reports) {
            r.setStatus(ReportStatus.CLOSED);
            repo.save(r);
        }
    }

    // -------------------------------------------------------
    //  METODI PRIVATI
    // -------------------------------------------------------

    private ReportDTO toDTO(Report r) {
        ReportDTO dto = new ReportDTO();
        dto.setId(r.getId());
        dto.setReporterId(r.getReporterId());
        dto.setReportedUserId(r.getReportedUserId());
        dto.setDescription(r.getDescription());
        dto.setStatus(r.getStatus().name());
        dto.setCreatedAt(r.getCreatedAt());
        return dto;
    }
}
