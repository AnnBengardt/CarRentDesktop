package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.*;
import com.AnnaMarunko.CarRentDesktop.repos.RateRepo;
import com.AnnaMarunko.CarRentDesktop.repos.RentRepo;
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
class RentServiceTest {

    @Autowired
    RentService rentService;

    @MockBean
    RentRepo rentRepo;

    Rent rent = new Rent();
    Rent rent1 = new Rent();
    Office office = new Office();
    Client client = new Client();
    Car car1 = new Car();

    @BeforeEach
    void setUp() {
        client.setIsBlackListed(false);
        client.setPhone("+7(903)555-55-55");
        client.setPassport("4515 708432");
        client.setClientId(Long.valueOf(1));
        client.setFirstName("Ivan");
        client.setLastName("Ivanov");
        client.setDriverLicense("0583967902386");

        office.setOfficeId(Long.valueOf(1));
        office.setHouse("24");
        office.setEmail("office@office.ru");
        office.setStreet("Leningradskiy Avenue");
        office.setCity("Moscow");

        car1.setCarId(Long.valueOf(2));
        car1.setStatus(true);
        car1.setStartingPrice(5000.00);
        car1.setInsurance(null);
        car1.setOffice(office);
        car1.setBrand("BMW");

        rent.setRentId(Long.valueOf(1));
        rent.setStartDate(null);
        rent.setEndDate(null);
        rent.setRate(null);
        rent.setFinalPrice(500.00);
        rent.setCar(car1);
        rent.setClient(client);

        rent1.setRentId(Long.valueOf(2));
        rent.setStartDate(null);
        rent.setEndDate(null);
        rent.setRate(null);
        rent.setFinalPrice(5000.00);
        rent.setCar(car1);
        rent.setClient(client);

    }

    @Test
    void create() {
        doReturn(rent1).when(rentRepo).save(rent1);

        // Execute the service call
        Rent returnedValue = rentService.create(rent1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved rent should not be null");
    }

    @Test
    void update() {
        rent1.setFinalPrice(10000.00);
        doReturn(rent1).when(rentRepo).save(rent1);

        // Execute the service call
        Rent returnedValue = rentService.create(rent1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved rent should not be null");
        Assertions.assertSame(rent1, returnedValue, "The rent returned was not the same as the mock");
    }

    @Test
    void delete() {
        doNothing().when(rentRepo).delete(rent1);

        // Execute the service call
        Boolean returnedValue = rentService.delete(rent1);

        // Assert the response
        Assertions.assertEquals(true, returnedValue, "The deleted rent should return true");


    }

    @Test
    void findAll() {
        doReturn(Arrays.asList(rent, rent1)).when(rentRepo).findAll();

        // Execute the service call
        List<Rent> rents = rentService.findAll();

        // Assert the response
        Assertions.assertEquals(2, rents.size(), "findAll should return 2 rents!");
    }

    @Test
    void find() {

        doReturn(Optional.of(rent)).when(rentRepo).findById(Long.valueOf(1));

        // Execute the service call
        Optional<Rent> returnedValue = rentService.find(Long.valueOf(1));

        // Assert the response
        Assertions.assertTrue(returnedValue.isPresent(), "Rent was not found");
        Assertions.assertSame(rent, returnedValue.get(), "The rent returned was not the same as the mock");

    }

    @Test
    void findNotFound() {
        doReturn(Optional.empty()).when(rentRepo).findById(Long.valueOf(5));

        // Execute the service call
        Optional<Rent> returnedValue = rentService.find(Long.valueOf(5));

        // Assert the response
        Assertions.assertFalse(returnedValue.isPresent(), "Rent should not be found");
    }

    @Test
    void findByClientId() {
        doReturn(Arrays.asList(rent, rent1)).when(rentRepo).findByClient_ClientId(Long.valueOf(1));

        // Execute the service call
        List<Rent> rents = rentService.findByClientId(Long.valueOf(1));

        // Assert the response
        Assertions.assertEquals(2, rents.size(), "findByOfficeId should return 2 rents!");
    }

    @Test
    void findByOfficeId() {
        doReturn(Arrays.asList(rent, rent1)).when(rentRepo).findByCar_Office_OfficeId(Long.valueOf(1));

        // Execute the service call
        List<Rent> rents = rentService.findByOfficeId(Long.valueOf(1));

        // Assert the response
        Assertions.assertEquals(2, rents.size(), "findByOfficeId should return 2 rents!");
    }

    @Test
    void findByCarId() {
        doReturn(Arrays.asList(rent, rent1)).when(rentRepo).findByCar_CarId(Long.valueOf(2));

        // Execute the service call
        List<Rent> rents = rentService.findByCarId(Long.valueOf(2));

        // Assert the response
        Assertions.assertEquals(2, rents.size(), "findByOfficeId should return 2 rents!");
    }
}