package com.logitrack.service.dto;

import lombok.Getter;

@Getter
public class VehicleDTO {
    private Long id;
    private String plate;
    private String model;
    private String category;
    private String description;

    public VehicleDTO(Long id, String plate, String model, String category) {
        this.id = id;
        this.plate = plate;
        this.model = model;
        this.category = category;
        this.description = plate + " - " + model;
    }
}
