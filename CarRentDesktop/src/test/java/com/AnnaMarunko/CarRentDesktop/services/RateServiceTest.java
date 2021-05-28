package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Rate;
import com.AnnaMarunko.CarRentDesktop.repos.RateRepo;
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
class RateServiceTest {

    @Autowired
    RateService rateService;

    @MockBean
    RateRepo rateRepo;

    Rate rate = new Rate();
    Rate rate1 = new Rate();

    @BeforeEach
    void setUp() {
        rate.setRateId(Long.valueOf(1));
        rate.setRateName("Good");
        rate.setPrice(500.00);

        rate1.setRateId(Long.valueOf(2));
        rate1.setRateName("Bad");
        rate1.setPrice(5000.00);

    }

    @Test
    void create() {
        doReturn(rate1).when(rateRepo).save(rate1);

        // Execute the service call
        Rate returnedValue = rateService.create(rate1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved rate should not be null");
    }

    @Test
    void update() {
        rate1.setPrice(10000.00);
        doReturn(rate1).when(rateRepo).save(rate1);

        // Execute the service call
        Rate returnedValue = rateService.create(rate1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved rate should not be null");
        Assertions.assertSame(rate1, returnedValue, "The rate returned was not the same as the mock");
    }

    @Test
    void delete() {
        doNothing().when(rateRepo).delete(rate1);

        // Execute the service call
        Boolean returnedValue = rateService.delete(rate1);

        // Assert the response
        Assertions.assertEquals(true, returnedValue, "The deleted rate should return true");


    }

    @Test
    void findAll() {
        doReturn(Arrays.asList(rate, rate1)).when(rateRepo).findAll();

        // Execute the service call
        List<Rate> rates = rateService.findAll();

        // Assert the response
        Assertions.assertEquals(2, rates.size(), "findAll should return 2 rates!");
    }

    @Test
    void find() {

        doReturn(Optional.of(rate)).when(rateRepo).findById(Long.valueOf(1));

        // Execute the service call
        Optional<Rate> returnedValue = rateService.find(Long.valueOf(1));

        // Assert the response
        Assertions.assertTrue(returnedValue.isPresent(), "Rate was not found");
        Assertions.assertSame(rate, returnedValue.get(), "The rate returned was not the same as the mock");

    }

    @Test
    void findNotFound() {
        doReturn(Optional.empty()).when(rateRepo).findById(Long.valueOf(5));

        // Execute the service call
        Optional<Rate> returnedValue = rateService.find(Long.valueOf(5));

        // Assert the response
        Assertions.assertFalse(returnedValue.isPresent(), "Rate should not be found");
    }
}