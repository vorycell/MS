package com.example.service;

import com.example.entity.Car;
import com.example.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    void testGetAllCars() {
        // Arrange
        Car car1 = new Car();
        Car car2 = new Car();
        List<Car> cars = Arrays.asList(car1, car2);
        when(carRepository.findAll()).thenReturn(cars);

        // Act
        List<Car> result = carService.getAllCars();

        // Assert
        assertEquals(2, result.size());
        assertEquals(cars, result);
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarById_Found() {
        // Arrange
        Car car = new Car();
        car.setId(1L);
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        // Act
        Optional<Car> result = carService.getCarById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(car, result.get());
        verify(carRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCarById_NotFound() {
        // Arrange
        when(carRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Car> result = carService.getCarById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(carRepository, times(1)).findById(999L);
    }

    @Test
    void testCreateCar() {
        // Arrange
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Camry");
        car.setYear(2020);
        when(carRepository.save(car)).thenReturn(car);

        // Act
        Car result = carService.createCar(car);

        // Assert
        assertNotNull(result);
        assertEquals("Toyota", result.getBrand());
        assertEquals("Camry", result.getModel());
        assertEquals(2020, result.getYear());
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testUpdateCar_Found() {
        // Arrange
        Car existingCar = new Car();
        existingCar.setId(1L);
        existingCar.setBrand("Honda");
        existingCar.setModel("Civic");
        existingCar.setYear(2018);

        Car newCar = new Car();
        newCar.setBrand("Toyota");
        newCar.setModel("Corolla");
        newCar.setYear(2021);

        when(carRepository.findById(1L)).thenReturn(Optional.of(existingCar));
        when(carRepository.save(existingCar)).thenReturn(existingCar);

        // Act
        Optional<Car> result = carService.updateCar(1L, newCar);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Toyota", result.get().getBrand());
        assertEquals("Corolla", result.get().getModel());
        assertEquals(2021, result.get().getYear());
        verify(carRepository, times(1)).findById(1L);
        verify(carRepository, times(1)).save(existingCar);
    }

    @Test
    void testUpdateCar_NotFound() {
        // Arrange
        Car newCar = new Car();
        newCar.setBrand("Toyota");
        when(carRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Car> result = carService.updateCar(999L, newCar);

        // Assert
        assertFalse(result.isPresent());
        verify(carRepository, times(1)).findById(999L);
        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    void testDeleteCar_Success() {
        // Arrange
        when(carRepository.existsById(1L)).thenReturn(true);
        doNothing().when(carRepository).deleteById(1L);

        // Act
        boolean result = carService.deleteCar(1L);

        // Assert
        assertTrue(result);
        verify(carRepository, times(1)). existsById(1L);
        verify(carRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCar_NotFound() {
        // Arrange
        when(carRepository.existsById(999L)).thenReturn(false);

        // Act
        boolean result = carService.deleteCar(999L);

        // Assert
        assertFalse(result);
        verify(carRepository, times(1)).existsById(999L);
        verify(carRepository, never()).deleteById(anyLong());
    }
}