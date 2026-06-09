
package com.skillmatch.user_service.controller;

import com.skillmatch.user_service.dto.ReportDTO;
import com.skillmatch.user_service.model.Report;
import com.skillmatch.user_service.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService service;

    @PostMapping
    public ReportDTO create(
            @RequestParam Long reporterId,
            @RequestParam Long reportedUserId,
            @RequestParam String description) {

        return service.create(reporterId, reportedUserId, description);
    }
}