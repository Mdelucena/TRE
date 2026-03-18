package com.logitrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.logitrack.domain.Trip;

import java.math.BigDecimal;
import java.util.List;

public interface DashboardRepository extends JpaRepository<Trip, Long> {

    @Query(value = """
            SELECT COALESCE(SUM(t.mileage), 0)
            FROM trips t
            WHERE (:vehicleId IS NULL OR t.vehicle_id = :vehicleId)
            """, nativeQuery = true)
    BigDecimal totalMileage(@Param("vehicleId") Long vehicleId);

    @Query(value = """
            SELECT v.category, COUNT(t.id)
            FROM trips t
            JOIN vehicles v ON v.id = t.vehicle_id
            GROUP BY v.category
            """, nativeQuery = true)
    List<Object[]> tripVolumeByCategory();

    @Query(value = """
            SELECT m.id,
                   v.plate,
                   v.model,
                   m.start_date,
                   m.expected_end_date,
                   m.service_type,
                   m.status,
                   m.estimated_cost
            FROM maintenance_schedules m
            JOIN vehicles v ON v.id = m.vehicle_id
            WHERE m.start_date >= CURRENT_DATE
            ORDER BY m.start_date ASC
            LIMIT 5
            """, nativeQuery = true)
    List<Object[]> nextFiveMaintenance();

    @Query(value = """
            SELECT v.id, v.plate, v.model, COALESCE(SUM(t.mileage), 0) AS total_mileage
            FROM vehicles v
            LEFT JOIN trips t ON t.vehicle_id = v.id
            GROUP BY v.id, v.plate, v.model
            ORDER BY total_mileage DESC
            LIMIT 1
            """, nativeQuery = true)
    List<Object[]> utilizationRankingTop1();

    @Query(value = """
            SELECT COALESCE(SUM(m.estimated_cost), 0)
            FROM maintenance_schedules m
            WHERE EXTRACT(MONTH FROM m.start_date) = EXTRACT(MONTH FROM CURRENT_DATE)
              AND EXTRACT(YEAR FROM m.start_date) = EXTRACT(YEAR FROM CURRENT_DATE)
            """, nativeQuery = true)
    BigDecimal currentMonthMaintenanceProjection();
}
