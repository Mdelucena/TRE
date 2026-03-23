package com.logitrack.service.mapper;

import com.logitrack.domain.MaintenanceSchedule;
import com.logitrack.domain.MaintenanceStatus;
import com.logitrack.domain.ServiceType;
import com.logitrack.domain.Vehicle;
import com.logitrack.service.dto.MaintenanceDTO;

import java.math.BigDecimal;

public final class MaintenanceMapper {

    private MaintenanceMapper() {
    }

    public static MaintenanceDTO toDto(MaintenanceSchedule maintenance) {
        MaintenanceDTO dto = new MaintenanceDTO();
        dto.setId(maintenance.getId());
        dto.setVehicleId(maintenance.getVehicle().getId());
        dto.setVehicleDescription(maintenance.getVehicle().getPlate() + " - " + maintenance.getVehicle().getModel());
        dto.setServiceType(maintenance.getServiceType().toString());
        dto.setScheduledDate(maintenance.getStartDate());
        dto.setExpectedEndDate(maintenance.getExpectedEndDate());
        dto.setEstimatedCost(maintenance.getEstimatedCost());
        dto.setStatus(maintenance.getStatus().toString());
        dto.setDescription("");
        return dto;
    }

    public static MaintenanceSchedule fromCreateRequest(MaintenanceDTO dto, Vehicle vehicle) {
        MaintenanceSchedule maintenance = new MaintenanceSchedule();
        maintenance.setVehicle(vehicle);
        maintenance.setStartDate(dto.getScheduledDate());
        maintenance.setExpectedEndDate(dto.getExpectedEndDate() != null ? dto.getExpectedEndDate() : dto.getScheduledDate());
        maintenance.setServiceType(parseServiceType(dto.getServiceType()));
        maintenance.setStatus(parseStatus(dto.getStatus()));
        maintenance.setEstimatedCost(dto.getEstimatedCost() != null ? dto.getEstimatedCost() : BigDecimal.ZERO);
        return maintenance;
    }

    public static void updateEntity(MaintenanceSchedule maintenance, MaintenanceDTO dto, Vehicle vehicle) {
        maintenance.setVehicle(vehicle);
        maintenance.setStartDate(dto.getScheduledDate());
        maintenance.setExpectedEndDate(dto.getExpectedEndDate() != null ? dto.getExpectedEndDate() : dto.getScheduledDate());
        maintenance.setServiceType(parseServiceType(dto.getServiceType()));
        maintenance.setStatus(parseStatus(dto.getStatus()));
        maintenance.setEstimatedCost(dto.getEstimatedCost() != null ? dto.getEstimatedCost() : BigDecimal.ZERO);
    }

    private static ServiceType parseServiceType(String serviceType) {
        if (serviceType == null || serviceType.isBlank()) {
            throw new IllegalArgumentException("Tipo de servico invalido");
        }

        String normalized = serviceType.trim().toUpperCase();
        if ("TROCA_OLEO".equals(normalized)) {
            normalized = "OLEO";
        }

        try {
            return ServiceType.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Tipo de servico invalido: " + serviceType);
        }
    }

    private static MaintenanceStatus parseStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status invalido");
        }

        String normalized = status.trim().toUpperCase();
        if ("SCHEDULED".equals(normalized)) {
            normalized = "PENDENTE";
        }

        try {
            return MaintenanceStatus.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Status invalido: " + status);
        }
    }
}
