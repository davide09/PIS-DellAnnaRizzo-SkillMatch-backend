package com.skillmatch.user_service.service;

import com.skillmatch.user_service.dto.CommissionConfigDTO;
import com.skillmatch.user_service.model.CommissionConfig;
import com.skillmatch.user_service.repository.CommissionConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommissionService {

    private final CommissionConfigRepository repo;

    public CommissionConfigDTO getConfig() {
        CommissionConfig c = repo.findAll().stream().findFirst()
                .orElseGet(() -> {
                    CommissionConfig def = new CommissionConfig();
                    def.setPercentage(8.0); // default 8%
                    return repo.save(def);
                });
        return toDTO(c);
    }

    public CommissionConfigDTO updatePercentage(double p) {
        CommissionConfig c = repo.findAll().stream().findFirst()
                .orElseGet(() -> {
                    CommissionConfig def = new CommissionConfig();
                    def.setPercentage(8.0);
                    return repo.save(def);
                });
        c.setPercentage(p);
        return toDTO(repo.save(c));
    }



    private CommissionConfigDTO toDTO(CommissionConfig c) {
        CommissionConfigDTO dto = new CommissionConfigDTO();
        dto.setPercentage(c.getPercentage());
        return dto;
    }
}
