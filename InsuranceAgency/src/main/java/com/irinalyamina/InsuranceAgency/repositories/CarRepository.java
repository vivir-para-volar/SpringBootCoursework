package com.irinalyamina.InsuranceAgency.repositories;

import com.irinalyamina.InsuranceAgency.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarRepository extends JpaRepository<Car, Long> {

    boolean existsByVin(String vin);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT * FROM car WHERE vin = :vin AND id <> :id) THEN 'true' ELSE 'false' END",
            nativeQuery = true)
    boolean existsByVinExceptId(Long id, String vin);

}