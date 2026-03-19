package com.logitrack.service.dto;

import java.math.BigDecimal;

public class TopVehicleUsage {
    private Long vehicleId;
    private String plate;
    private String model;
    private BigDecimal totalMileage;

    public TopVehicleUsage(Long vehicleId, String plate, String model, BigDecimal totalMileage) {
        this.vehicleId = vehicleId;
        this.plate = plate;
        this.model = model;
        this.totalMileage = totalMileage;
    }

    public Long getVehicleId() {
        return vehicleId;
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

    public BigDecimal getTotalMileage() {
        return totalMileage;
    }
}
