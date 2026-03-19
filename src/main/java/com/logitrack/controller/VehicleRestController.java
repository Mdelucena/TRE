package com.logitrack.controller;

import com.logitrack.domain.Vehicle;
import com.logitrack.repository.VehicleRepository;
import com.logitrack.service.dto.VehicleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class VehicleRestController {

    private final VehicleRepository vehicleRepository;

    public VehicleRestController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping
    public List<VehicleDTO> list() {
        return vehicleRepository.findAll().stream()
                .map(v -> new VehicleDTO(v.getId(), v.getPlate(), v.getModel(), v.getCategory().toString()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getById(@PathVariable Long id) {
        return vehicleRepository.findById(id)
                .map(v -> new VehicleDTO(v.getId(), v.getPlate(), v.getModel(), v.getCategory().toString()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
