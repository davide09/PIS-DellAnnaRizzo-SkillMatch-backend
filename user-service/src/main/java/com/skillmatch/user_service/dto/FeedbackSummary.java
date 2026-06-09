
// src/main/java/com/skillmatch/user_service/dto/FeedbackSummary.java
package com.skillmatch.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedbackSummary {
    private double average;
    private long count;
    private String level;
}