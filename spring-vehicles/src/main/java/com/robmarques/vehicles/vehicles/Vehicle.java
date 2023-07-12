package com.robmarques.vehicles.vehicles;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "veiculo")
    @NotBlank
    private String vehicle;

    @Column(name = "marca")
    @NotBlank
    private String brand;

    @Column(name = "ano")
    @NotNull
    @Min(1884)
    private Short year;

    @Column(name = "descricao")
    @NotBlank
    private String description;

    @Column(name = "cor")
    @NotBlank
    private String color;

    @Column(name = "vendido")
    private Boolean sold = false;

    @CreationTimestamp
    @Column(name = "created")
    private Instant created;

    @UpdateTimestamp
    @Column(name = "updated")
    private Instant updated;

    public Vehicle(String vehicle, String brand, short year, String description, String color) {
        this.vehicle = vehicle;
        this.brand = brand;
        this.year = year;
        this.description = description;
        this.color = color;
    }


}
