package com.logitrack.service.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class DashboardResponseDTO {
    private Long totalVehicles;
    private Long totalScheduledMaintenances;
    private Long totalInProgressMaintenances;
    private Long totalCompletedMaintenances;
    private List<CategoryVolume> vehiclesByCategory;
    private List<MaintenanceTimelineItem> maintenanceTimeline;
    private List<TopVehicleUsage> topVehicleUsage;

    public DashboardResponseDTO(Long totalVehicles, Long totalScheduledMaintenances,
                                Long totalInProgressMaintenances, Long totalCompletedMaintenances,
                                List<CategoryVolume> vehiclesByCategory,
                                List<MaintenanceTimelineItem> maintenanceTimeline,
                                List<TopVehicleUsage> topVehicleUsage) {
        this.totalVehicles = totalVehicles;
        this.totalScheduledMaintenances = totalScheduledMaintenances;
        this.totalInProgressMaintenances = totalInProgressMaintenances;
        this.totalCompletedMaintenances = totalCompletedMaintenances;
        this.vehiclesByCategory = vehiclesByCategory;
        this.maintenanceTimeline = maintenanceTimeline;
        this.topVehicleUsage = topVehicleUsage;
    }
}
