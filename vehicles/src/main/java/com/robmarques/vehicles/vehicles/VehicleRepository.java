package com.robmarques.vehicles.vehicles;

import com.robmarques.vehicles.vehicles.dtos.VehicleBrandDTO;
import com.robmarques.vehicles.vehicles.dtos.VehicleDecadeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    @Query(value = """
                SELECT brand brand, count(*) vehicleCount 
                FROM Vehicle
                GROUP BY brand
                ORDER BY brand
            """)
    List<VehicleBrandDTO> groupVehiclesByBrand();

    @Query(value = """
                SELECT date_part('year', date_trunc('decade',to_date(CAST (v.ano AS varchar), 'yyyy')))
                as birthDecade, COUNT(*) vehiclesCount
                FROM vehicles v
                group by birthDecade
                order by birthDecade;
            """, nativeQuery = true)
    List<VehicleDecadeDTO> groupVehiclesByDecade();

    @Query(value = """
            SELECT * FROM vehicles v
            where DATE(v.created) BETWEEN NOW() - INTERVAL '1 WEEK' AND NOW()
            """, nativeQuery = true)
    List<Vehicle> getVehiclesRegisteredLastWeek();

    Long countVehicleBySoldFalse();


}