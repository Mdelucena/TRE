package com.logitrack.controller;

import com.logitrack.service.DashboardService;

public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
}
