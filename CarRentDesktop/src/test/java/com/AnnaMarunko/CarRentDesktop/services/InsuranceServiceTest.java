package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Car;
import com.AnnaMarunko.CarRentDesktop.entities.Insurance;
import com.AnnaMarunko.CarRentDesktop.repos.InsuranceRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class InsuranceServiceTest {

    @Autowired
    InsuranceService insuranceService;

    @MockBean
    InsuranceRepo insuranceRepo;

    Insurance insurance = new Insurance();
    Insurance insurance1 = new Insurance();
    Car car = new Car();

    @BeforeEach
    void setUp() {
        car.setCarId(Long.valueOf(1));
        car.setStatus(true);
        car.setStartingPrice(3000.00);
        car.setInsurance(insurance);
        car.setOffice(null);
        car.setBrand("Fiat");

        insurance.setInsuranceId(Long.valueOf(1));
        insurance.setStartDate(null);
        insurance.setPrice(30000.00);
        insurance.setEndDate(null);
        insurance.setCar(car);

        insurance1.setInsuranceId(Long.valueOf(2));
        insurance1.setStartDate(null);
        insurance1.setPrice(20000.00);
        insurance1.setEndDate(null);
        insurance1.setCar(null);
    }

    @Test
    @DisplayName("Create an insurance")
    void create() {
        doReturn(insurance1).when(insuranceRepo).save(insurance1);

        // Execute the service call
        Insurance returnedValue = insuranceService.create(insurance1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved insurance should not be null");
    }

    @Test
    @DisplayName("Update an insurance")
    void update() {
        insurance1.setPrice(25000.00);
        doReturn(insurance1).when(insuranceRepo).save(insurance1);

        // Execute the service call
        Insurance returnedValue = insuranceService.update(insurance1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved insurance should not be null");
        Assertions.assertSame(insurance1, returnedValue, "The insurance returned was not the same as the mock");
    }

    @Test
    @DisplayName("Delete an insurance")
    void delete() {
        doNothing().when(insuranceRepo).delete(insurance1);

        // Execute the service call
        Boolean returnedValue = insuranceService.delete(insurance1);

        // Assert the response
        Assertions.assertEquals(true, returnedValue, "The deleted insurance should return true");


    }

    @Test
    @DisplayName("Find all insurances")
    void findAll() {
        doReturn(Arrays.asList(insurance, insurance1)).when(insuranceRepo).findAll();

        // Execute the service call
        List<Insurance> insurances = insuranceService.findAll();

        // Assert the response
        Assertions.assertEquals(2, insurances.size(), "findAll should return 2 insurances!");
    }

    @Test
    @DisplayName("Find an insurance by ID")
    void find() {

        doReturn(Optional.of(insurance)).when(insuranceRepo).findById(Long.valueOf(1));

        // Execute the service call
        Optional<Insurance> returnedValue = insuranceService.find(Long.valueOf(1));

        // Assert the response
        Assertions.assertTrue(returnedValue.isPresent(), "Insurance was not found");
        Assertions.assertSame(insurance, returnedValue.get(), "The insurance returned was not the same as the mock");

    }

    @Test
    @DisplayName("Find an insurance by ID (fail)")
    void findNotFound() {
        doReturn(Optional.empty()).when(insuranceRepo).findById(Long.valueOf(5));

        // Execute the service call
        Optional<Insurance> returnedValue = insuranceService.find(Long.valueOf(5));

        // Assert the response
        Assertions.assertFalse(returnedValue.isPresent(), "Insurance should not be found");
    }

    @Test
    @DisplayName("Find an insurance by Car id")
    void findByCarId() {

        doReturn(Optional.of(insurance)).when(insuranceRepo).findByCar_CarId(Long.valueOf(1));

        // Execute the service call
        Optional<Insurance> returnedValue = insuranceService.findByCarId(Long.valueOf(1));

        // Assert the response
        Assertions.assertTrue(returnedValue.isPresent(), "Insurance was not found");
        Assertions.assertSame(insurance, returnedValue.get(), "The insurance returned was not the same as the mock");

    }
}