package com.logitrack.controller;

import com.logitrack.service.DashboardService;

// DEPRECATED: Use DashboardRestController para React
// Este controller era para Thymeleaf (server-side rendering)
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
}
