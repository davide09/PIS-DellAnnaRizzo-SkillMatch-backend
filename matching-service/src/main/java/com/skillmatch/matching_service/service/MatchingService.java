
package com.skillmatch.matching_service.service;

import com.skillmatch.matching_service.dto.MatchResultDTO;
import com.skillmatch.matching_service.dto.ProjectDTO;
import com.skillmatch.matching_service.dto.UserDTO;
import com.skillmatch.matching_service.dto.UserMatchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final RestTemplate restTemplate;

    @Value("${matching.project-service-url}")
    private String projectServiceUrl;

    @Value("${matching.user-service-url}")
    private String userServiceUrl;

    public MatchResultDTO match(Long projectId) {

        // prende il progetto dal project-service (endpoint internal)
        ProjectDTO project = restTemplate.getForObject(
                projectServiceUrl + "/" + projectId,
                ProjectDTO.class
        );

        // prende tutti gli utenti dal user-service (endpoint internal)
        ResponseEntity<UserDTO[]> response = restTemplate.exchange(
                userServiceUrl,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                UserDTO[].class
        );

        UserDTO[] users = response.getBody();

        if (project == null || users == null) {
            throw new RuntimeException("Errore nel recupero dati");
        }

        List<String> requiredSkills = project.getRequiredSkills();
        List<UserMatchDTO> ranking = new ArrayList<>();

        // matching 1:1 sulle skill
        for (UserDTO user : users) {

            //ESCLUDO ADMIN E COMPANY dal match
            if (!"PROFESSIONAL".equalsIgnoreCase(user.getRole())) {
                continue;
            }

            int matched = 0;

            for (String required : requiredSkills) {
                boolean hasSkill = user.getSkills().stream()
                        .anyMatch(s -> s.equalsIgnoreCase(required));
                if (hasSkill) {
                    matched++;
                }
            }

            int requiredLevel = convertLevelSafe(project.getExperienceLevel());
            int userLevel = convertLevelSafe(user.getReputationLevel());

            if (userLevel < requiredLevel) {
                matched = (int) Math.max(1, Math.floor(matched * 0.5));
            }

            if (matched > 0) {
                ranking.add(new UserMatchDTO(
                        user.getId(),
                        matched,
                        requiredSkills.size()
                ));
            }
        }

        // ordina per numero di skill matchate
        ranking.sort((a, b) -> Integer.compare(b.getMatchedSkills(), a.getMatchedSkills()));

        List<Long> matchedUsers = ranking.stream()
                .map(UserMatchDTO::getUserId)
                .collect(Collectors.toList());

        return new MatchResultDTO(projectId, matchedUsers, ranking);
    }
    private int convertLevelSafe(String level) {
        if (level == null || level.isBlank()) {
            return 0; // livello più basso di tutti
        }
        return convertLevel(level);
    }
    private int convertLevel(String level) {
        return switch (level.toUpperCase()) {
            case "JUNIOR" -> 1;
            case "AFFIDABILE" -> 2;
            case "TOP PERFORMER", "TOP_PERFORMER" -> 3;
            default -> 0;
        };
    }

}
