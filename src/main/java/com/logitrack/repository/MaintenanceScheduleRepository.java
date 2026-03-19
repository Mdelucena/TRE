package com.logitrack.repository;

import com.logitrack.domain.MaintenanceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceScheduleRepository extends JpaRepository<MaintenanceSchedule, Long> {
    List<MaintenanceSchedule> findByVehicleId(Long vehicleId);
}
