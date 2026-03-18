package com.logitrack.controller;

import com.logitrack.repository.VehicleRepository;
import com.logitrack.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;
    private final VehicleRepository vehicleRepository;

    public DashboardController(DashboardService dashboardService, VehicleRepository vehicleRepository) {
        this.dashboardService = dashboardService;
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) Long vehicleId, Model model) {
        model.addAttribute("dashboard", dashboardService.buildDashboard(vehicleId));
        model.addAttribute("vehicles", vehicleRepository.findAll());
        model.addAttribute("selectedVehicleId", vehicleId);
        return "dashboard";
    }
}
