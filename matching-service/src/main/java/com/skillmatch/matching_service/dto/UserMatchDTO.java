
package com.skillmatch.matching_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMatchDTO {

    private Long userId;
    private int matchedSkills;
    private int totalSkills;
}