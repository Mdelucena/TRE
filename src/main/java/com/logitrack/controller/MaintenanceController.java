package com.logitrack.controller;

import com.logitrack.domain.MaintenanceSchedule;
import com.logitrack.domain.MaintenanceStatus;
import com.logitrack.domain.ServiceType;
import com.logitrack.repository.MaintenanceScheduleRepository;
import com.logitrack.repository.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manutencoes")
public class MaintenanceController {

    private final MaintenanceScheduleRepository maintenanceRepository;
    private final VehicleRepository vehicleRepository;

    public MaintenanceController(MaintenanceScheduleRepository maintenanceRepository, VehicleRepository vehicleRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @ModelAttribute("statusList")
    public MaintenanceStatus[] statusList() {
        return MaintenanceStatus.values();
    }

    @ModelAttribute("serviceTypeList")
    public ServiceType[] serviceTypeList() {
        return ServiceType.values();
    }

    @ModelAttribute("vehicles")
    public Object vehicles() {
        return vehicleRepository.findAll();
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("maintenances", maintenanceRepository.findAll());
        return "maintenance/list";
    }

    @GetMapping("/novo")
    public String createForm(Model model) {
        model.addAttribute("maintenance", new MaintenanceSchedule());
        return "maintenance/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("maintenance") MaintenanceSchedule maintenance,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "maintenance/form";
        }
        maintenanceRepository.save(maintenance);
        return "redirect:/manutencoes";
    }

    @GetMapping("/{id}/editar")
    public String editForm(@PathVariable Long id, Model model) {
        MaintenanceSchedule maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Manutencao nao encontrada: " + id));
        model.addAttribute("maintenance", maintenance);
        return "maintenance/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("maintenance") MaintenanceSchedule maintenance,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "maintenance/form";
        }
        maintenance.setId(id);
        maintenanceRepository.save(maintenance);
        return "redirect:/manutencoes";
    }

    @PostMapping("/{id}/excluir")
    public String delete(@PathVariable Long id) {
        maintenanceRepository.deleteById(id);
        return "redirect:/manutencoes";
    }
}
