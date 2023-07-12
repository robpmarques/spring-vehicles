package com.robmarques.vehicles.vehicles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robmarques.vehicles.exception.VehicleBadParamsException;
import com.robmarques.vehicles.exception.VehicleNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleRepository vehicleRepository;

    @Test
    public void vehicleFindSuccess() throws Exception {
        var uuid = UUID.randomUUID();
        Vehicle vehicle = generateVehicleObject();
        vehicle.setId(uuid);

        when(vehicleRepository.findById(uuid)).thenReturn(Optional.of(vehicle));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles/{id}", uuid)).andExpect(status().isOk());
    }

    @Test
    public void vehicleFindFailure_NotFound() throws Exception {
        var uuid = UUID.randomUUID();

        when(vehicleRepository.findById(any(UUID.class))).thenThrow(VehicleNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles/{id}", uuid)).andExpect(status().isNotFound());
    }

    @Test
    public void vehicleCreationSuccess() throws Exception {
        Vehicle vehicle = generateVehicleObject();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/vehicles").contentType(MediaType.APPLICATION_JSON).content(asJsonString(vehicle))).andExpect(status().isCreated());
    }

    @Test
    public void vehicleCreationFailure_BadParams() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicle("Onix");

        when(vehicleRepository.save(vehicle)).thenThrow(VehicleBadParamsException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/vehicles").contentType(MediaType.APPLICATION_JSON).content(asJsonString(vehicle))).andExpect(status().isBadRequest());
    }

    @Test
    public void vehicleReplaceSuccess() throws Exception {
        var uuid = UUID.randomUUID();

        Vehicle vehicle = generateVehicleObject();
        vehicle.setId(uuid);
        Vehicle newVehicle = new Vehicle("Civic", "Honda", (short) 2023, "Descrição Civic Honda", "Branco");

        when(vehicleRepository.findById(uuid)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/vehicles/{id}", vehicle.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(newVehicle))).andExpect(status().isOk()).andExpect(jsonPath("$.vehicle").value(newVehicle.getVehicle()));
    }

    @Test
    public void vehicleReplaceFailure_NotFound() throws Exception {
        Vehicle vehicle = generateVehicleObject();
        Vehicle newVehicle = new Vehicle("Civic", "Honda", (short) 2023, "Descrição Civic Honda", "Branco");

        when(vehicleRepository.findById(UUID.randomUUID())).thenThrow(VehicleNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/vehicles/{id}", vehicle.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(newVehicle))).andExpect(status().isNotFound());
    }

    @Test
    public void vehicleReplaceFailure_BadRequest() throws Exception {
        var uuid = UUID.randomUUID();
        Vehicle vehicle = generateVehicleObject();
        vehicle.setId(uuid);
        Vehicle newVehicle = new Vehicle();
        newVehicle.setVehicle("Civic");

        when(vehicleRepository.findById(uuid)).thenReturn(Optional.of(vehicle));

        var vehicleCopied = immutableCopyProperties(newVehicle, vehicle);
        when(vehicleRepository.save(vehicleCopied)).thenThrow(VehicleBadParamsException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/vehicles/{id}", vehicle.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(newVehicle))).andExpect(status().isBadRequest());
    }

    @Test
    public void vehiclePartialUpdateSuccess() throws Exception {
        var uuid = UUID.randomUUID();
        Vehicle vehicle = generateVehicleObject();
        vehicle.setId(uuid);
        Vehicle newVehicle = new Vehicle();
        newVehicle.setSold(true);

        when(vehicleRepository.findById(uuid)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/vehicles/{id}", vehicle.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(newVehicle))).andExpect(status().isOk()).andExpect(jsonPath("$.sold").value(newVehicle.getSold()));
    }

    @Test
    public void vehiclePartialUpdateFailure_NotFound() throws Exception {
        Vehicle vehicle = generateVehicleObject();
        Vehicle newVehicle = new Vehicle();
        newVehicle.setSold(true);

        when(vehicleRepository.findById(UUID.randomUUID())).thenThrow(VehicleNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/vehicles/{id}", vehicle.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(newVehicle))).andExpect(status().isNotFound());
    }

    @Test
    public void vehiclePartialUpdateFailure_BadParams() throws Exception {
        var id = UUID.randomUUID();

        Vehicle vehicle = generateVehicleObject();
        vehicle.setId(id);
        Map<String, String> wrongParams = new HashMap<>();
        wrongParams.put("sold", "WrongType");

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/vehicles/{id}", vehicle.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(wrongParams))).andExpect(status().isBadRequest());
    }

    @Test
    public void vehicleGetAvailableSuccess() throws Exception {
        var vehicles = List.of(generateVehicleObject());

        var expectedString = String.format("Número de veículos disponíveis: %s", vehicles.size());

        when(vehicleRepository.countVehicleBySoldFalse()).thenReturn((long) vehicles.size());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles/available")).andExpect(status().isOk()).andExpect(content().string(expectedString));
    }

    private Vehicle immutableCopyProperties(Vehicle source, Vehicle target) {
        var vehicle = new Vehicle();

        BeanUtils.copyProperties(target, vehicle);
        BeanUtils.copyProperties(source, vehicle, "id", "created", "updated");

        return vehicle;
    }

    private Vehicle generateVehicleObject() {
        return new Vehicle("Onix", "Chevrolet", (short) 2024, "Descrição Onix Chevrolet", "Vermelho");
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}