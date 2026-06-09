
package com.skillmatch.matching_service.events;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MatchEvent {
    private Long projectId;
    private List<Long> topProfessionals;
    private LocalDateTime timestamp;
}
