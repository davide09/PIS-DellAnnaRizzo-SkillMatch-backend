
package com.skillmatch.matching_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchResultDTO {

    private Long projectId;
    private List<Long> matchedUsers;
    private List<UserMatchDTO> ranking;
}