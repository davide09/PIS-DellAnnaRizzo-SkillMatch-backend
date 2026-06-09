
package com.skillmatch.user_service.controller;

import com.skillmatch.user_service.dto.CommissionConfigDTO;
import com.skillmatch.user_service.model.CommissionConfig;
import com.skillmatch.user_service.service.CommissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/internal/commission")
@RequiredArgsConstructor
public class InternalCommissionController {

    private final CommissionService commissionService;

    @GetMapping
    public CommissionConfigDTO get() {
        return commissionService.getConfig();
    }
}