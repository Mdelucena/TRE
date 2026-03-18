package com.logitrack.service;

import com.logitrack.repository.DashboardRepository;
import com.logitrack.service.dto.CategoryVolume;
import com.logitrack.service.dto.DashboardData;
import com.logitrack.service.dto.MaintenanceTimelineItem;
import com.logitrack.service.dto.TopVehicleUsage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public DashboardData buildDashboard(Long vehicleId) {
        BigDecimal totalMileage = dashboardRepository.totalMileage(vehicleId);
        List<CategoryVolume> volumes = dashboardRepository.tripVolumeByCategory()
                .stream()
                .map(row -> new CategoryVolume(String.valueOf(row[0]), ((Number) row[1]).longValue()))
                .toList();

        List<MaintenanceTimelineItem> maintenances = dashboardRepository.nextFiveMaintenance()
                .stream()
                .map(this::mapMaintenance)
                .toList();

        TopVehicleUsage topUsage = mapTopUsage(dashboardRepository.utilizationRankingTop1());
        BigDecimal monthlyProjection = dashboardRepository.currentMonthMaintenanceProjection();

        return new DashboardData(
                totalMileage,
                volumes,
                maintenances,
                topUsage,
                monthlyProjection
        );
    }

    private MaintenanceTimelineItem mapMaintenance(Object[] row) {
        return new MaintenanceTimelineItem(
                ((Number) row[0]).longValue(),
                String.valueOf(row[1]),
                String.valueOf(row[2]),
                toLocalDate(row[3]),
                toLocalDate(row[4]),
                String.valueOf(row[5]),
                String.valueOf(row[6]),
                toBigDecimal(row[7])
        );
    }

    private TopVehicleUsage mapTopUsage(List<Object[]> rows) {
        if (rows == null || rows.isEmpty()) {
            return new TopVehicleUsage(null, "-", "Sem dados", BigDecimal.ZERO);
        }

        Object[] row = rows.get(0);
        if (row != null && row.length == 1 && row[0] instanceof Object[] nestedRow) {
            row = nestedRow;
        }

        if (row == null || row.length < 4) {
            return new TopVehicleUsage(null, "-", "Sem dados", BigDecimal.ZERO);
        }

        return new TopVehicleUsage(
                ((Number) row[0]).longValue(),
                String.valueOf(row[1]),
                String.valueOf(row[2]),
                toBigDecimal(row[3])
        );
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value instanceof BigDecimal decimal) {
            return decimal;
        }
        if (value instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue());
        }
        return BigDecimal.ZERO;
    }

    private LocalDate toLocalDate(Object value) {
        if (value instanceof LocalDate localDate) {
            return localDate;
        }
        if (value instanceof Date date) {
            return date.toLocalDate();
        }
        if (value instanceof Timestamp timestamp) {
            return timestamp.toLocalDateTime().toLocalDate();
        }
        if (value instanceof LocalDateTime dateTime) {
            return dateTime.toLocalDate();
        }
        throw new IllegalArgumentException("Tipo de data nao suportado: " + value);
    }
}
