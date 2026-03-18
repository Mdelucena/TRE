package com.logitrack.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MaintenanceTimelineItem(
        Long maintenanceId,
        String plate,
        String model,
        LocalDate startDate,
        LocalDate expectedEndDate,
        String serviceType,
        String status,
        BigDecimal estimatedCost
) {
}
