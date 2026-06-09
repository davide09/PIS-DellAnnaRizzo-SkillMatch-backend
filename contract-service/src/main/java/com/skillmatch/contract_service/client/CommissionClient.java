
package com.skillmatch.contract_service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CommissionClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public double getCommissionPercentage() {
        String url = "http://user-service:8083/api/users/internal/commission";
        var obj = restTemplate.getForObject(url, CommissionResponse.class);
        return obj.percentage();
    }

    public record CommissionResponse(Double percentage) {}
}