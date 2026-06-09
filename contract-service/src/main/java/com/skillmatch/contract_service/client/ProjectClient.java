
package com.skillmatch.contract_service.client;

import com.skillmatch.contract_service.dto.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProjectClient {

    private final RestTemplate restTemplate;

    public String getProjectTitle(Long projectId) {

        String url = "http://project-service:8081/api/projects/internal/" + projectId;

        ProjectResponse response = restTemplate.getForObject(url, ProjectResponse.class);

        return (response != null) ? response.getTitle() : null;
    }
}