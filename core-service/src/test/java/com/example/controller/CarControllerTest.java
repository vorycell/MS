package com.example.controller;

import com.example.entity.Car;
import com.example.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @Test
    void testGetAllCars() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carService.getAllCars()).thenReturn(cars);

        List<Car> result = carController.getAllCars();

        assertEquals(2, result.size());
        verify(carService, times(1)).getAllCars();
    }

    @Test
    void testGetCarById_Found() {
        Car car = new Car();
        when(carService.getCarById(1L)).thenReturn(Optional.of(car));

        ResponseEntity<Car> response = carController.getCarById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(car, response.getBody());
        verify(carService).getCarById(1L);
    }

    @Test
    void testGetCarById_NotFound() {
        when(carService.getCarById(999L)).thenReturn(Optional.empty());

        ResponseEntity<Car> response = carController.getCarById(999L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testCreateCar() {
        Car car = new Car();
        when(carService.createCar(car)).thenReturn(car);

        Car result = carController.createCar(car);

        assertEquals(car, result);
        verify(carService).createCar(car);
    }

    @Test
    void testUpdateCar_Found() {
        Car updatedCar = new Car();
        when(carService.updateCar(1L, updatedCar)).thenReturn(Optional.of(updatedCar));

        ResponseEntity<Car> response = carController.updateCar(1L, updatedCar);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedCar, response.getBody());
    }

    @Test
    void testUpdateCar_NotFound() {
        Car updatedCar = new Car();
        when(carService.updateCar(999L, updatedCar)).thenReturn(Optional.empty());

        ResponseEntity<Car> response = carController.updateCar(999L, updatedCar);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteCar_Success() {
        when(carService.deleteCar(1L)).thenReturn(true);

        ResponseEntity<Void> response = carController.deleteCar(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDeleteCar_NotFound() {
        when(carService.deleteCar(999L)).thenReturn(false);

        ResponseEntity<Void> response = carController.deleteCar(999L);

        assertEquals(404, response.getStatusCodeValue());
    }
}
