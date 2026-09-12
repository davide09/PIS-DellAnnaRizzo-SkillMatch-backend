
package com.skillmatch.user_service.controller;

import com.skillmatch.user_service.dto.ReportDTO;
import com.skillmatch.user_service.model.Report;
import com.skillmatch.user_service.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users/admin/reports")
@RequiredArgsConstructor
public class AdminReportController {

    private final ReportService service;


    @GetMapping
    public List<ReportDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ReportDTO getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<ReportDTO> getUserReports(@PathVariable Long userId) {
        return service.getReportsForUser(userId);
    }

    @GetMapping("/user/{userId}/latest")
    public Optional<ReportDTO> getLatestUserReport(@PathVariable Long userId) {
        return service.getLatestReportForUser(userId);
    }

    @PostMapping("/{id}/close")
    public ReportDTO close(@PathVariable Long id) {
        return service.closeReport(id);
    }

    @PostMapping("/user/{userId}/closeAll")
    public void closeAll(@PathVariable Long userId) {
        service.closeAllForUser(userId);
    }
}