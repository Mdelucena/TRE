package com.logitrack.service.dto;

import java.math.BigDecimal;

public record TopVehicleUsage(Long vehicleId, String plate, String model, BigDecimal totalMileage) {
}
