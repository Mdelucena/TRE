package com.logitrack.controller;

import com.logitrack.service.DashboardService;
import com.logitrack.service.dto.DashboardResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class DashboardRestController {

    private final DashboardService dashboardService;

    public DashboardRestController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponseDTO getDashboard(@RequestParam(required = false) Long vehicleId) {
        return dashboardService.buildDashboard(vehicleId);
    }
}
