package com.logitrack.service.dto;

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

    public Long getId() {
        return id;
    }

    public String getPlate() {
        return plate;
    }

    public String getModel() {
        return model;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
