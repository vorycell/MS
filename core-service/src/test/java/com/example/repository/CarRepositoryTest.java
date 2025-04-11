package com.example.repository;

import com.example.entity.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        // Очищаем таблицу cars перед каждым тестом
        entityManager.getEntityManager()
                .createQuery("DELETE FROM Car")
                .executeUpdate();
        entityManager.clear(); // Очищаем кэш Hibernate
    }

    @Test
    void testFindAll() {
        // Arrange
        Car car1 = new Car();
        car1.setBrand("Toyota");
        car1.setModel("Camry");
        car1.setYear(2020);
        carRepository.save(car1);

        Car car2 = new Car();
        car2.setBrand("Honda");
        car2.setModel("Civic");
        car2.setYear(2018);
        carRepository.save(car2);

        // Act
        List<Car> cars = carRepository.findAll();

        // Assert
        assertEquals(2, cars.size());
        assertTrue(cars.stream().anyMatch(car -> car.getBrand().equals("Toyota") && car.getModel().equals("Camry")));
        assertTrue(cars.stream().anyMatch(car -> car.getBrand().equals("Honda") && car.getModel().equals("Civic")));
    }

    @Test
    void testFindById_Found() {
        // Arrange
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Camry");
        car.setYear(2020);
        Car savedCar = carRepository.save(car);

        // Act
        Optional<Car> foundCar = carRepository.findById(savedCar.getId());

        // Assert
        assertTrue(foundCar.isPresent());
        assertEquals("Toyota", foundCar.get().getBrand());
        assertEquals("Camry", foundCar.get().getModel());
        assertEquals(2020, foundCar.get().getYear());
    }

    @Test
    void testFindById_NotFound() {
        // Act
        Optional<Car> foundCar = carRepository.findById(999L);

        // Assert
        assertFalse(foundCar.isPresent());
    }

    @Test
    void testSave() {
        // Arrange
        Car car = new Car();
        car.setBrand("Audi");
        car.setModel("A4");
        car.setYear(2023);

        // Act
        Car savedCar = carRepository.save(car);

        // Assert
        assertNotNull(savedCar.getId());
        assertEquals("Audi", savedCar.getBrand());
        assertEquals("A4", savedCar.getModel());
        assertEquals(2023, savedCar.getYear());
    }

    @Test
    void testExistsById() {
        // Arrange
        Car car = new Car();
        car.setBrand("Ford");
        car.setModel("Focus");
        car.setYear(2019);
        Car savedCar = carRepository.save(car);

        // Act & Assert
        assertTrue(carRepository.existsById(savedCar.getId()));
        assertFalse(carRepository.existsById(999L));
    }

    @Test
    void testDeleteById() {
        // Arrange
        Car car = new Car();
        car.setBrand("Nissan");
        car.setModel("Altima");
        car.setYear(2021);
        Car savedCar = carRepository.save(car);

        // Act
        carRepository.deleteById(savedCar.getId());

        // Assert
        assertFalse(carRepository.existsById(savedCar.getId()));
    }
}