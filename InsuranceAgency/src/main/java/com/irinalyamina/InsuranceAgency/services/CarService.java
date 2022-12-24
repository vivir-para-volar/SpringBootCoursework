package com.irinalyamina.InsuranceAgency.services;

import com.irinalyamina.InsuranceAgency.models.Car;
import com.irinalyamina.InsuranceAgency.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> list() {
        return carRepository.findAll();
    }

    public Car create(Car car) {
        return carRepository.save(car);
    }

    public void edit(Car car) {
        carRepository.save(car);
    }

    public Car getById(Long id) {
        return carRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    public boolean checkVin(String vin) {
        return carRepository.existsByVin(vin);
    }

    public boolean checkVinExceptId(Long id, String vin) {
        return carRepository.existsByVinExceptId(id, vin);
    }
}