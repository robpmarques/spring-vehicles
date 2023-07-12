package com.robmarques.vehicles.vehicles;

import com.robmarques.vehicles.exception.VehicleBadParamsException;
import com.robmarques.vehicles.exception.VehicleNotFoundException;
import com.robmarques.vehicles.helpers.NullAwareBeanUtils;
import com.robmarques.vehicles.vehicles.dtos.VehicleBrandDTO;
import com.robmarques.vehicles.vehicles.dtos.VehicleDecadeDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class VehicleController {
    private final VehicleRepository vehicleRepository;

    public VehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> searchVehicles(@RequestParam(required = false, name = "marca") String brand, @RequestParam(required = false, name = "ano") Short year, @RequestParam(required = false, name = "cor") String color) {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setColor(color);
        vehicle.setYear(year);

        var vehicles = vehicleRepository.findAll(Example.of(vehicle));

        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable UUID id) {
        var vehicle = vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));

        return ResponseEntity.ok(vehicle);
    }

    @PostMapping("/vehicles")
    @Transactional
    public ResponseEntity<Vehicle> insertVehicle(@Valid @RequestBody Vehicle vehicle, BindingResult result) {
        if (result.hasErrors()) throw new VehicleBadParamsException(result);

        vehicleRepository.save(vehicle);

        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @PutMapping("/vehicles/{id}")
    @Transactional
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody Vehicle newVehicle, @PathVariable UUID id, BindingResult result) {
        if (result.hasErrors()) throw new VehicleBadParamsException(result);

        var vehicle = vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
        BeanUtils.copyProperties(newVehicle, vehicle, "id", "created", "updated");

        vehicleRepository.save(vehicle);

        return ResponseEntity.ok(vehicle);
    }

    @PatchMapping("/vehicles/{id}")
    @Transactional
    public ResponseEntity<Vehicle> partialUpdateVehicle(@RequestBody Vehicle vehicleParams, @PathVariable UUID id) {
        var vehicle = vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
        NullAwareBeanUtils.copyNonNullProperties(vehicleParams, vehicle, "id");

        vehicleRepository.save(vehicle);

        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/vehicles/weekly")
    @Transactional
    public ResponseEntity<List<Vehicle>> getWeeklyCreatedVehicles() {
        var vehicles = vehicleRepository.getVehiclesRegisteredLastWeek();

        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/vehicles/available")
    @Transactional
    public ResponseEntity<String> getAvailableVehicles() {
        var vehiclesAvailableCount = vehicleRepository.countVehicleBySoldFalse();

        return ResponseEntity.ok(String.format("Número de veículos disponíveis: %s", vehiclesAvailableCount));
    }

    @GetMapping("/vehicles/decades")
    @Transactional
    public ResponseEntity<List<VehicleDecadeDTO>> groupVehiclesByDecade() {
        var vehiclesDecade = vehicleRepository.groupVehiclesByDecade();

        return ResponseEntity.ok(vehiclesDecade);
    }

    @GetMapping("/vehicles/brands")
    @Transactional
    public ResponseEntity<List<VehicleBrandDTO>> groupVehiclesByBrand() {
        var vehiclesDecade = vehicleRepository.groupVehiclesByBrand();

        return ResponseEntity.ok(vehiclesDecade);
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<?> deleteVehicle(UUID id) {
        Vehicle deleteVehicle = vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));

        vehicleRepository.delete(deleteVehicle);

        return ResponseEntity.noContent().build();
    }
}
