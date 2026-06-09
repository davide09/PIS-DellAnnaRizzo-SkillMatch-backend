
package com.skillmatch.matching_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/admin/matching")
@RequiredArgsConstructor
public class AdminMatchingController {

    @GetMapping("/stats")
    public String stats() {
        return "Feature coming soon: matching statistics.";
    }

    @GetMapping("/logs")
    public String logs() {
        return "Feature coming soon: matching logs.";
    }
}