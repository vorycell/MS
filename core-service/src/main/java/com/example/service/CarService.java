package com.example.service;

import com.example.entity.Car;
import com.example.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    public Optional<Car> updateCar(Long id, Car newCar) {
        return carRepository.findById(id).map(car -> {
            car.setBrand(newCar.getBrand());
            car.setModel(newCar.getModel());
            car.setYear(newCar.getYear());
            return carRepository.save(car);
        });
    }

    public boolean deleteCar(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
