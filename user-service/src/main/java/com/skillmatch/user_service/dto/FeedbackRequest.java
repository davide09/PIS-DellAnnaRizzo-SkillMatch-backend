
// src/main/java/com/skillmatch/user_service/dto/FeedbackRequest.java
package com.skillmatch.user_service.dto;

import lombok.Data;

@Data
public class FeedbackRequest {
    private Long contractId;
    private Long professionalId;
    private Integer rating;
    private String comment;
}