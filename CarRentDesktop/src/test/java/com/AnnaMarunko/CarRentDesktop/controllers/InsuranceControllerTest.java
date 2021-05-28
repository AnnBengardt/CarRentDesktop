package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Car;
import com.AnnaMarunko.CarRentDesktop.entities.Insurance;
import com.AnnaMarunko.CarRentDesktop.services.InsuranceService;
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

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class InsuranceControllerTest {

    @MockBean
    private InsuranceService insuranceService;

    @Autowired
    private MockMvc mockMvc;

    Insurance insurance = new Insurance();
    Insurance insurance1 = new Insurance();
    Car car = new Car();

    @BeforeEach
    void setUp() {

        car.setCarId(Long.valueOf(1));
        car.setStatus(true);
        car.setStartingPrice(3000.00);
        car.setBrand("Fiat");

        insurance.setInsuranceId(Long.valueOf(1));
        insurance.setPrice(30000.00);
        insurance.setCar(car);

        insurance1.setInsuranceId(Long.valueOf(2));
        insurance1.setPrice(20000.00);

    }

    @Test
    void create() throws Exception {
        doReturn(insurance).when(insuranceService).create(any());

        // Execute the POST request
        mockMvc.perform(post("/api/insurances")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(insurance)))

                // Validate the response code and content type
                .andExpect(status().is(201));

    }

    @Test
    void findAll() throws Exception {
        doReturn(Lists.newArrayList(insurance, insurance1)).when(insuranceService).findAll();

        // Execute the GET request
        mockMvc.perform(get("/api/insurances"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].insuranceId", is(1)))
                .andExpect(jsonPath("$[0].price", is(30000.00)))
                .andExpect(jsonPath("$[0].car.carId", is(1)))
                .andExpect(jsonPath("$[0].car.status", is(true)))
                .andExpect(jsonPath("$[0].car.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$[0].car.brand", is("Fiat")))
                .andExpect(jsonPath("$[1].insuranceId", is(2)))
                .andExpect(jsonPath("$[1].price", is(20000.00)));
    }

    @Test
    void find() throws Exception {
        doReturn(Optional.of(insurance)).when(insuranceService).find(Long.valueOf(1));

        // Execute the GET request
        mockMvc.perform(get("/api/insurances/1"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.insuranceId", is(1)))
                .andExpect(jsonPath("$.price", is(30000.00)))
                .andExpect(jsonPath("$.car.carId", is(1)))
                .andExpect(jsonPath("$.car.status", is(true)))
                .andExpect(jsonPath("$.car.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$.car.brand", is("Fiat")));
    }

    @Test
    void updateInsurance() throws Exception {
        Insurance insurance2 = insurance;
        insurance2.setPrice(33000.00);
        doReturn(Optional.of(insurance)).when(insuranceService).find(Long.valueOf(1));
        doReturn(insurance2).when(insuranceService).update(insurance);

        // Execute the POST request
        mockMvc.perform(put("/api/insurances/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(asJsonString(insurance2)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.insuranceId", is(1)))
                .andExpect(jsonPath("$.price", is(33000.00)))
                .andExpect(jsonPath("$.car.carId", is(1)))
                .andExpect(jsonPath("$.car.status", is(true)))
                .andExpect(jsonPath("$.car.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$.car.brand", is("Fiat")));
    }

    @Test
    void deleteInsurance() throws Exception {
        doReturn(Optional.of(insurance)).when(insuranceService).find(Long.valueOf(1));
        doReturn(true).when(insuranceService).delete(insurance);
        mockMvc.perform(delete("/api/insurances/{id}", 1))
                .andExpect(status().isOk());
    }


    @Test
    void findByCarId() throws Exception {
        doReturn(Optional.of(insurance)).when(insuranceService).findByCarId(Long.valueOf(1));

        // Execute the GET request
        mockMvc.perform(get("/api/insurances/findbycar/1"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.insuranceId", is(1)))
                .andExpect(jsonPath("$.price", is(30000.00)))
                .andExpect(jsonPath("$.car.carId", is(1)))
                .andExpect(jsonPath("$.car.status", is(true)))
                .andExpect(jsonPath("$.car.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$.car.brand", is("Fiat")));
    }



    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}