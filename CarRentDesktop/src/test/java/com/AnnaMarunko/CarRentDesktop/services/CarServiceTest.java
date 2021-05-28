package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Car;
import com.AnnaMarunko.CarRentDesktop.entities.Office;
import com.AnnaMarunko.CarRentDesktop.repos.CarRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class CarServiceTest {

    @Autowired
    CarService carService;

    @MockBean
    CarRepo carRepo;

    Car car = new Car();
    Car car1 = new Car();
    Office office = new Office();

    @BeforeEach
    void setUp() {
        office.setOfficeId(Long.valueOf(1));
        office.setHouse("24");
        office.setEmail("office@office.ru");
        office.setStreet("Leningradskiy Avenue");
        office.setCity("Moscow");

        car.setCarId(Long.valueOf(1));
        car.setStatus(true);
        car.setStartingPrice(3000.00);
        car.setInsurance(null);
        car.setOffice(office);
        car.setBrand("Fiat");

        car1.setCarId(Long.valueOf(2));
        car1.setStatus(true);
        car1.setStartingPrice(5000.00);
        car1.setInsurance(null);
        car1.setOffice(office);
        car1.setBrand("BMW");
    }

    @Test
    void create() {
        doReturn(car1).when(carRepo).save(car1);

        // Execute the service call
        Car returnedValue = carService.create(car1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved car should not be null");
    }

    @Test
    void update() {
        car1.setBrand("Volkswagen");
        doReturn(car1).when(carRepo).save(car1);

        // Execute the service call
        Car returnedValue = carService.create(car1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved car should not be null");
        Assertions.assertSame(car1, returnedValue, "The car returned was not the same as the mock");
    }

    @Test
    void delete() {
        doNothing().when(carRepo).delete(car1);

        // Execute the service call
        Boolean returnedValue = carService.delete(car1);

        // Assert the response
        Assertions.assertEquals(true, returnedValue, "The deleted car should return true");


    }

    @Test
    void findAll() {
        doReturn(Arrays.asList(car, car1)).when(carRepo).findAll();

        // Execute the service call
        List<Car> cars = carService.findAll();

        // Assert the response
        Assertions.assertEquals(2, cars.size(), "findAll should return 2 cars!");
    }

    @Test
    void find() {

        doReturn(Optional.of(car)).when(carRepo).findById(Long.valueOf(1));

        // Execute the service call
        Optional<Car> returnedValue = carService.find(Long.valueOf(1));

        // Assert the response
        Assertions.assertTrue(returnedValue.isPresent(), "Car was not found");
        Assertions.assertSame(car, returnedValue.get(), "The car returned was not the same as the mock");

    }

    @Test
    void findNotFound() {
        doReturn(Optional.empty()).when(carRepo).findById(Long.valueOf(22));

        // Execute the service call
        Optional<Car> returnedValue = carService.find(Long.valueOf(22));

        // Assert the response
        Assertions.assertFalse(returnedValue.isPresent(), "Car should not be found");
    }

    @Test
    void findByOfficeId() {

        doReturn(Arrays.asList(car, car1)).when(carRepo).findByOffice_OfficeId(Long.valueOf(1));

        // Execute the service call
        List<Car> cars = carService.findByOfficeId(Long.valueOf(1));

        // Assert the response
        Assertions.assertEquals(2, cars.size(), "findByOfficeId should return 2 cars!");


    }
}