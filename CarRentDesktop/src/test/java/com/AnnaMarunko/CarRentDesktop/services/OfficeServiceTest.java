package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Job;
import com.AnnaMarunko.CarRentDesktop.entities.Office;
import com.AnnaMarunko.CarRentDesktop.repos.JobRepo;
import com.AnnaMarunko.CarRentDesktop.repos.OfficeRepo;
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
class OfficeServiceTest {

    @Autowired
    OfficeService officeService;

    @MockBean
    OfficeRepo officeRepo;

    Office office = new Office();
    Office office1 = new Office();

    @BeforeEach
    void setUp() {
        office.setOfficeId(Long.valueOf(1));
        office.setHouse("24");
        office.setEmail("office@office.ru");
        office.setStreet("Leningradskiy Avenue");
        office.setCity("Moscow");

        office1.setOfficeId(Long.valueOf(2));
        office1.setHouse("35");
        office1.setEmail("office1@office.ru");
        office1.setStreet("Tsvetnoy boulevard");
        office1.setCity("Moscow");

    }

    @Test
    void create() {
        doReturn(office1).when(officeRepo).save(office1);

        // Execute the service call
        Office returnedValue = officeService.create(office1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved office should not be null");
    }

    @Test
    void update() {
        office1.setEmail("newoffice@office.ru");
        doReturn(office1).when(officeRepo).save(office1);

        // Execute the service call
        Office returnedValue = officeService.create(office1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved office should not be null");
        Assertions.assertSame(office1, returnedValue, "The office returned was not the same as the mock");
    }

    @Test
    void delete() {
        doNothing().when(officeRepo).delete(office1);

        // Execute the service call
        Boolean returnedValue = officeService.delete(office1);

        // Assert the response
        Assertions.assertEquals(true, returnedValue, "The deleted office should return true");


    }

    @Test
    void findAll() {
        doReturn(Arrays.asList(office, office1)).when(officeRepo).findAll();

        // Execute the service call
        List<Office> offices = officeService.findAll();

        // Assert the response
        Assertions.assertEquals(2, offices.size(), "findAll should return 2 offices!");
    }

    @Test
    void find() {

        doReturn(Optional.of(office)).when(officeRepo).findById(Long.valueOf(1));

        // Execute the service call
        Optional<Office> returnedValue = officeService.find(Long.valueOf(1));

        // Assert the response
        Assertions.assertTrue(returnedValue.isPresent(), "Office was not found");
        Assertions.assertSame(office, returnedValue.get(), "The office returned was not the same as the mock");

    }

    @Test
    void findNotFound() {
        doReturn(Optional.empty()).when(officeRepo).findById(Long.valueOf(5));

        // Execute the service call
        Optional<Office> returnedValue = officeService.find(Long.valueOf(5));

        // Assert the response
        Assertions.assertFalse(returnedValue.isPresent(), "Office should not be found");
    }
}