package com.logitrack.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MaintenanceTimelineItem {
    private Long maintenanceId;
    private String plate;
    private String model;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private String serviceType;
    private String status;
    private BigDecimal estimatedCost;

    public MaintenanceTimelineItem(Long maintenanceId, String plate, String model,
                                   LocalDate startDate, LocalDate expectedEndDate,
                                   String serviceType, String status, BigDecimal estimatedCost) {
        this.maintenanceId = maintenanceId;
        this.plate = plate;
        this.model = model;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.serviceType = serviceType;
        this.status = status;
        this.estimatedCost = estimatedCost;
    }

    public Long getMaintenanceId() {
        return maintenanceId;
    }

    public String getPlate() {
        return plate;
    }

    public String getModel() {
        return model;
    }

    public String getVehicleDescription() {
        return plate + " - " + model;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpectedEndDate() {
        return expectedEndDate;
    }

    public LocalDate getScheduledDate() {
        return startDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getEstimatedCost() {
        return estimatedCost;
    }
}
