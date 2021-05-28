package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Car;
import com.AnnaMarunko.CarRentDesktop.entities.Office;
import com.AnnaMarunko.CarRentDesktop.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @MockBean
    private CarService carService;

    @Autowired
    private MockMvc mockMvc;

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
        car.setOffice(office);
        car.setBrand("Fiat");

        car1.setCarId(Long.valueOf(2));
        car1.setStatus(true);
        car1.setStartingPrice(5000.00);
        car1.setOffice(office);
        car1.setBrand("BMW");

    }

    @Test
    void create() throws Exception {
        doReturn(car).when(carService).create(any());

        // Execute the POST request
        mockMvc.perform(post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(car)))

                // Validate the response code and content type
                .andExpect(status().is(201));

    }

    @Test
    void findAll() throws Exception {
        doReturn(Lists.newArrayList(car, car1)).when(carService).findAll();

        // Execute the GET request
        mockMvc.perform(get("/api/cars"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].carId", is(1)))
                .andExpect(jsonPath("$[0].status", is(true)))
                .andExpect(jsonPath("$[0].startingPrice", is(3000.00)))
                .andExpect(jsonPath("$[0].brand", is("Fiat")))
                .andExpect( jsonPath("$[0].office.officeId", is(1)))
                .andExpect( jsonPath("$[0].office.house", is("24")))
                .andExpect( jsonPath("$[0].office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$[0].office.city", is("Moscow")))
                .andExpect( jsonPath("$[0].office.email", is("office@office.ru")))
                .andExpect(jsonPath("$[1].carId", is(2)))
                .andExpect(jsonPath("$[1].status", is(true)))
                .andExpect(jsonPath("$[1].startingPrice", is(5000.00)))
                .andExpect(jsonPath("$[1].brand", is("BMW")))
                .andExpect( jsonPath("$[1].office.officeId", is(1)))
                .andExpect( jsonPath("$[1].office.house", is("24")))
                .andExpect( jsonPath("$[1].office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$[1].office.city", is("Moscow")))
                .andExpect( jsonPath("$[1].office.email", is("office@office.ru")));
    }

    @Test
    void find() throws Exception {
        doReturn(Optional.of(car)).when(carService).find(Long.valueOf(1));

        // Execute the GET request
        mockMvc.perform(get("/api/cars/1"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.carId", is(1)))
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$.brand", is("Fiat")))
                .andExpect( jsonPath("$.office.officeId", is(1)))
                .andExpect( jsonPath("$.office.house", is("24")))
                .andExpect( jsonPath("$.office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$.office.city", is("Moscow")))
                .andExpect( jsonPath("$.office.email", is("office@office.ru")));
    }

    @Test
    void updateCar() throws Exception {
        Car car2 = car;
        car2.setBrand("Audi");
        doReturn(Optional.of(car)).when(carService).find(Long.valueOf(1));
        doReturn(car2).when(carService).update(car);

        // Execute the POST request
        mockMvc.perform(put("/api/cars/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(asJsonString(car2)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.carId", is(1)))
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$.brand", is("Audi")))
                .andExpect( jsonPath("$.office.officeId", is(1)))
                .andExpect( jsonPath("$.office.house", is("24")))
                .andExpect( jsonPath("$.office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$.office.city", is("Moscow")))
                .andExpect( jsonPath("$.office.email", is("office@office.ru")));
    }

    @Test
    void deleteCar() throws Exception {
        doReturn(Optional.of(car)).when(carService).find(Long.valueOf(1));
        doReturn(true).when(carService).delete(car);
        mockMvc.perform(delete("/api/cars/{id}", 1))
                .andExpect(status().isOk());
    }


    @Test
    void findByOfficeId() throws Exception {
        doReturn(Lists.newArrayList(car, car1)).when(carService).findByOfficeId(Long.valueOf(1));

        // Execute the GET request
        mockMvc.perform(get("/api/cars/findbyoffice/1"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].carId", is(1)))
                .andExpect(jsonPath("$[0].status", is(true)))
                .andExpect(jsonPath("$[0].startingPrice", is(3000.00)))
                .andExpect(jsonPath("$[0].brand", is("Fiat")))
                .andExpect( jsonPath("$[0].office.officeId", is(1)))
                .andExpect( jsonPath("$[0].office.house", is("24")))
                .andExpect( jsonPath("$[0].office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$[0].office.city", is("Moscow")))
                .andExpect( jsonPath("$[0].office.email", is("office@office.ru")))
                .andExpect(jsonPath("$[1].carId", is(2)))
                .andExpect(jsonPath("$[1].status", is(true)))
                .andExpect(jsonPath("$[1].startingPrice", is(5000.00)))
                .andExpect(jsonPath("$[1].brand", is("BMW")))
                .andExpect( jsonPath("$[1].office.officeId", is(1)))
                .andExpect( jsonPath("$[1].office.house", is("24")))
                .andExpect( jsonPath("$[1].office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$[1].office.city", is("Moscow")))
                .andExpect( jsonPath("$[1].office.email", is("office@office.ru")));
    }



    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    

}