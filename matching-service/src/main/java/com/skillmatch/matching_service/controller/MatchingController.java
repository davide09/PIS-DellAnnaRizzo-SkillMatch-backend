
package com.skillmatch.matching_service.controller;

import com.skillmatch.matching_service.dto.MatchResultDTO;
import com.skillmatch.matching_service.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matching")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @PostMapping("/run/{projectId}")
    public MatchResultDTO match(@PathVariable Long projectId) {
        return matchingService.match(projectId);
    }
}
