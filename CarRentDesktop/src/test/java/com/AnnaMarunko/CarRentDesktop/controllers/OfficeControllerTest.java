package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Office;
import com.AnnaMarunko.CarRentDesktop.entities.Rate;
import com.AnnaMarunko.CarRentDesktop.services.OfficeService;
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
import org.springframework.test.web.servlet.MockMvc;

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
class OfficeControllerTest {

    @MockBean
    private OfficeService officeService;

    @Autowired
    private MockMvc mockMvc;

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
    void create() throws Exception {
        doReturn(office).when(officeService).create(any());

        // Execute the POST request
        mockMvc.perform(post("/api/offices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(office)))

                // Validate the response code and content type
                .andExpect(status().is(201));

    }

    @Test
    void findAll() throws Exception {
        doReturn(Lists.newArrayList(office, office1)).when(officeService).findAll();

        // Execute the GET request
        mockMvc.perform(get("/api/offices"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].officeId", is(1)))
                .andExpect(jsonPath("$[0].email", is("office@office.ru")))
                .andExpect(jsonPath("$[0].city", is("Moscow")))
                .andExpect(jsonPath("$[0].street", is("Leningradskiy Avenue")))
                .andExpect(jsonPath("$[0].house", is("24")))
                .andExpect(jsonPath("$[1].officeId", is(2)))
                .andExpect(jsonPath("$[1].email", is("office1@office.ru")))
                .andExpect(jsonPath("$[1].city", is("Moscow")))
                .andExpect(jsonPath("$[1].street", is("Tsvetnoy boulevard")))
                .andExpect(jsonPath("$[1].house", is("35")));
    }

    @Test
    void find() throws Exception {
        doReturn(Optional.of(office)).when(officeService).find(Long.valueOf(1));

        // Execute the GET request
        mockMvc.perform(get("/api/offices/1"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.officeId", is(1)))
                .andExpect(jsonPath("$.email", is("office@office.ru")))
                .andExpect(jsonPath("$.city", is("Moscow")))
                .andExpect(jsonPath("$.street", is("Leningradskiy Avenue")))
                .andExpect(jsonPath("$.house", is("24")));
    }

    @Test
    void updateOffice() throws Exception {
        Office office2 = office;
        office2.setEmail("cars@yandex.ru");
        doReturn(Optional.of(office)).when(officeService).find(Long.valueOf(1));
        doReturn(office2).when(officeService).update(office);

        // Execute the POST request
        mockMvc.perform(put("/api/offices/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(asJsonString(office2)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.officeId", is(1)))
                .andExpect(jsonPath("$.email", is("cars@yandex.ru")))
                .andExpect(jsonPath("$.city", is("Moscow")))
                .andExpect(jsonPath("$.street", is("Leningradskiy Avenue")))
                .andExpect(jsonPath("$.house", is("24")));
    }

    @Test
    void deleteOffice() throws Exception {
        doReturn(Optional.of(office)).when(officeService).find(Long.valueOf(1));
        doReturn(true).when(officeService).delete(office);
        mockMvc.perform(delete("/api/offices/{id}", 1))
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