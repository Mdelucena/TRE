package com.logitrack.service.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardData(
        BigDecimal totalMileage,
        List<CategoryVolume> volumeByCategory,
        List<MaintenanceTimelineItem> nextMaintenances,
        TopVehicleUsage topVehicleUsage,
        BigDecimal monthlyMaintenanceProjection
) {
}
