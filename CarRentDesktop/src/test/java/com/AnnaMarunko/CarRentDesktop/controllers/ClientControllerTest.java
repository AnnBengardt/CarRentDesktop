package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Client;
import com.AnnaMarunko.CarRentDesktop.entities.Office;
import com.AnnaMarunko.CarRentDesktop.services.ClientService;
import com.AnnaMarunko.CarRentDesktop.services.OfficeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
class ClientControllerTest {

    @MockBean
    private ClientService clientService;

    @Autowired
    private MockMvc mockMvc;

    Client client = new Client();
    Client client1 = new Client();

    @BeforeEach
    void setUp() {

        client.setIsBlackListed(false);
        client.setPhone("+7(903)555-55-55");
        client.setPassport("4515 708432");
        client.setClientId(Long.valueOf(1));
        client.setFirstName("Ivan");
        client.setLastName("Ivanov");
        client.setDriverLicense("0583967902386");

        client1.setIsBlackListed(false);
        client1.setPhone("+7(903)444-33-55");
        client1.setPassport("4515 909812");
        client1.setClientId(Long.valueOf(2));
        client1.setFirstName("Alexandr");
        client1.setLastName("Pushkin");
        client1.setDriverLicense("0581234567886");

    }

    @Test
    @DisplayName("Create a client")
    void create() throws Exception {
        doReturn(client).when(clientService).create(any());

        // the POST request
        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(client)))

                // the response code
                .andExpect(status().is(201));

    }

    @Test
    @DisplayName("Find all clients")
    void findAll() throws Exception {
        doReturn(Lists.newArrayList(client, client1)).when(clientService).findAll();

        // the GET request
        mockMvc.perform(get("/api/clients"))
                // the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // the returned fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].clientId", is(1)))
                .andExpect(jsonPath("$[0].isBlackListed", is(false)))
                .andExpect(jsonPath("$[0].phone", is("+7(903)555-55-55")))
                .andExpect(jsonPath("$[0].passport", is("4515 708432")))
                .andExpect(jsonPath("$[0].driverLicense", is("0583967902386")))
                .andExpect(jsonPath("$[0].firstName", is("Ivan")))
                .andExpect(jsonPath("$[0].lastName", is("Ivanov")))
                .andExpect(jsonPath("$[1].clientId", is(2)))
                .andExpect(jsonPath("$[1].isBlackListed", is(false)))
                .andExpect(jsonPath("$[1].phone", is("+7(903)444-33-55")))
                .andExpect(jsonPath("$[1].passport", is("4515 909812")))
                .andExpect(jsonPath("$[1].driverLicense", is("0581234567886")))
                .andExpect(jsonPath("$[1].firstName", is("Alexandr")))
                .andExpect(jsonPath("$[1].lastName", is("Pushkin")));
    }

    @Test
    @DisplayName("Find a client by ID")
    void find() throws Exception {
        doReturn(Optional.of(client)).when(clientService).find(Long.valueOf(1));

        // the GET request
        mockMvc.perform(get("/api/clients/1"))
                // the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // the returned fields
                .andExpect(jsonPath("$.clientId", is(1)))
                .andExpect(jsonPath("$.isBlackListed", is(false)))
                .andExpect(jsonPath("$.phone", is("+7(903)555-55-55")))
                .andExpect(jsonPath("$.passport", is("4515 708432")))
                .andExpect(jsonPath("$.driverLicense", is("0583967902386")))
                .andExpect(jsonPath("$.firstName", is("Ivan")))
                .andExpect(jsonPath("$.lastName", is("Ivanov")));
    }

    @Test
    @DisplayName("Update a client")
    void updateClient() throws Exception {
        Client client2 = client;
        client2.setFirstName("Eugene");
        doReturn(Optional.of(client)).when(clientService).find(Long.valueOf(1));
        doReturn(client2).when(clientService).update(client);

        // the POST request
        mockMvc.perform(put("/api/clients/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(asJsonString(client2)))

                // the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // the returned fields
                .andExpect(jsonPath("$.clientId", is(1)))
                .andExpect(jsonPath("$.isBlackListed", is(false)))
                .andExpect(jsonPath("$.phone", is("+7(903)555-55-55")))
                .andExpect(jsonPath("$.passport", is("4515 708432")))
                .andExpect(jsonPath("$.driverLicense", is("0583967902386")))
                .andExpect(jsonPath("$.firstName", is("Eugene")))
                .andExpect(jsonPath("$.lastName", is("Ivanov")));
    }

    @Test
    @DisplayName("Delete a client")
    void deleteClient() throws Exception {
        doReturn(Optional.of(client)).when(clientService).find(Long.valueOf(1));
        doReturn(true).when(clientService).delete(client);

        // the DELETE request
        mockMvc.perform(delete("/api/clients/{id}", 1))
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