package com.logitrack.controller;

import com.logitrack.domain.MaintenanceSchedule;
import com.logitrack.domain.Vehicle;
import com.logitrack.repository.MaintenanceScheduleRepository;
import com.logitrack.repository.VehicleRepository;
import com.logitrack.service.dto.MaintenanceDTO;
import com.logitrack.service.mapper.MaintenanceMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/maintenance")
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class MaintenanceRestController {

    private final MaintenanceScheduleRepository maintenanceRepository;
    private final VehicleRepository vehicleRepository;

    public MaintenanceRestController(MaintenanceScheduleRepository maintenanceRepository,
                                     VehicleRepository vehicleRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping
    public List<MaintenanceDTO> list(@RequestParam(required = false) Long vehicleId) {
        List<MaintenanceSchedule> maintenances;
        if (vehicleId != null) {
            maintenances = maintenanceRepository.findByVehicleId(vehicleId);
        } else {
            maintenances = maintenanceRepository.findAll();
        }
        return maintenances.stream()
            .map(MaintenanceMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceDTO> getById(@PathVariable Long id) {
        return maintenanceRepository.findById(id)
                .map(m -> ResponseEntity.ok(MaintenanceMapper.toDto(m)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MaintenanceDTO> create(@Valid @RequestBody MaintenanceDTO maintenanceDTO) {
        Vehicle vehicle = vehicleRepository.findById(maintenanceDTO.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Veiculo nao encontrado"));

        MaintenanceSchedule maintenance = MaintenanceMapper.fromCreateRequest(maintenanceDTO, vehicle);

        MaintenanceSchedule saved = maintenanceRepository.save(maintenance);
        return ResponseEntity.status(HttpStatus.CREATED).body(MaintenanceMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody MaintenanceDTO maintenanceDTO) {
        if (!maintenanceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Vehicle vehicle = vehicleRepository.findById(maintenanceDTO.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Veiculo nao encontrado"));

        MaintenanceSchedule maintenance = maintenanceRepository.findById(id).get();
        MaintenanceMapper.updateEntity(maintenance, maintenanceDTO, vehicle);

        MaintenanceSchedule updated = maintenanceRepository.save(maintenance);
        return ResponseEntity.ok(MaintenanceMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!maintenanceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        maintenanceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
