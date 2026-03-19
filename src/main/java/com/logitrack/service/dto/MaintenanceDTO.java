package com.logitrack.service.dto;

import com.logitrack.domain.MaintenanceSchedule;

import java.time.LocalDate;

public class MaintenanceDTO {
    private Long id;
    private Long vehicleId;
    private String vehicleDescription;
    private String serviceType;
    private LocalDate scheduledDate;
    private String status;
    private String description;

    public MaintenanceDTO() {
    }

    public MaintenanceDTO(MaintenanceSchedule maintenance) {
        this.id = maintenance.getId();
        this.vehicleId = maintenance.getVehicle().getId();
        this.vehicleDescription = maintenance.getVehicle().getPlate() + " - " + maintenance.getVehicle().getModel();
        this.serviceType = maintenance.getServiceType().toString();
        this.scheduledDate = maintenance.getStartDate();
        this.status = maintenance.getStatus().toString();
        this.description = "";
    }

    public static MaintenanceSchedule toEntity(MaintenanceDTO dto) {
        MaintenanceSchedule maintenance = new MaintenanceSchedule();
        if (dto.id != null) {
            maintenance.setId(dto.id);
        }
        maintenance.setStartDate(dto.scheduledDate);
        maintenance.setExpectedEndDate(dto.scheduledDate);
        maintenance.setEstimatedCost(java.math.BigDecimal.ZERO);
        return maintenance;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleDescription() {
        return vehicleDescription;
    }

    public void setVehicleDescription(String vehicleDescription) {
        this.vehicleDescription = vehicleDescription;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
