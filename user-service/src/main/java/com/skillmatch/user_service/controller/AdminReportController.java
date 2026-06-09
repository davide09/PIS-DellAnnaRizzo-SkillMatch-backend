
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

    // 1. Tutte le segnalazioni
    @GetMapping
    public List<ReportDTO> getAll() {
        return service.getAll();
    }

    // 2. Una singola segnalazione
    @GetMapping("/{id}")
    public ReportDTO getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    // 3. Tutte le segnalazioni di un utente
    @GetMapping("/user/{userId}")
    public List<ReportDTO> getUserReports(@PathVariable Long userId) {
        return service.getReportsForUser(userId);
    }

    // 4. Ultima segnalazione aperta di quell’utente
    @GetMapping("/user/{userId}/latest")
    public Optional<ReportDTO> getLatestUserReport(@PathVariable Long userId) {
        return service.getLatestReportForUser(userId);
    }

    // 5. Chiudi una singola segnalazione
    @PostMapping("/{id}/close")
    public ReportDTO close(@PathVariable Long id) {
        return service.closeReport(id);
    }

    // 6. Chiudi tutte le segnalazioni di quell’utente
    @PostMapping("/user/{userId}/closeAll")
    public void closeAll(@PathVariable Long userId) {
        service.closeAllForUser(userId);
    }
}