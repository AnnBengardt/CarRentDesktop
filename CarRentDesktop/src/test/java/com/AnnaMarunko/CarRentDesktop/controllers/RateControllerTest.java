package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Rate;
import com.AnnaMarunko.CarRentDesktop.services.RateService;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RateControllerTest {

    @MockBean
    private RateService rateService;

    @Autowired
    private MockMvc mockMvc;

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
    void create() throws Exception {
        doReturn(rate).when(rateService).create(any());

        // Execute the POST request
        mockMvc.perform(post("/api/rates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(rate)))

                // Validate the response code and content type
                .andExpect(status().is(201));

    }

    @Test
    void findAll() throws Exception {
        doReturn(Lists.newArrayList(rate, rate1)).when(rateService).findAll();

        // Execute the GET request
        mockMvc.perform(get("/api/rates"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rateId", is(1)))
                .andExpect(jsonPath("$[0].rateName", is("Good")))
                .andExpect(jsonPath("$[0].price", is(500.00)))
                .andExpect(jsonPath("$[1].rateId", is(2)))
                .andExpect(jsonPath("$[1].rateName", is("Bad")))
                .andExpect(jsonPath("$[1].price", is(5000.00)));
    }

    @Test
    void find() throws Exception {
        doReturn(Optional.of(rate)).when(rateService).find(Long.valueOf(1));

        // Execute the GET request
        mockMvc.perform(get("/api/rates/1"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.rateId", is(1)))
                .andExpect(jsonPath("$.rateName", is("Good")))
                .andExpect(jsonPath("$.price", is(500.00)));
    }

    @Test
    void updateRate() throws Exception {
        Rate rate2 = rate;
        rate2.setPrice(600.00);
        doReturn(Optional.of(rate)).when(rateService).find(Long.valueOf(1));
        doReturn(rate2).when(rateService).update(rate);

        // Execute the POST request
        mockMvc.perform(put("/api/rates/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(asJsonString(rate2)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.rateId", is(1)))
                .andExpect(jsonPath("$.rateName", is("Good")))
                .andExpect(jsonPath("$.price", is(600.00)));
    }

    @Test
    void deleteRate() throws Exception {
        doReturn(Optional.of(rate)).when(rateService).find(Long.valueOf(1));
        doReturn(true).when(rateService).delete(rate);
        mockMvc.perform(delete("/api/rates/{id}", 1))

        .andExpect(status().isOk());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}