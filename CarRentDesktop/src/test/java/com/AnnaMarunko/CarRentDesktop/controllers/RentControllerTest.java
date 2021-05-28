package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.*;
import com.AnnaMarunko.CarRentDesktop.services.InsuranceService;
import com.AnnaMarunko.CarRentDesktop.services.RentService;
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
class RentControllerTest {

    @MockBean
    private RentService rentService;

    @Autowired
    private MockMvc mockMvc;

    Rent rent = new Rent();
    Rent rent1 = new Rent();
    Car car = new Car();
    Client client = new Client();
    Office office = new Office();

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

        car.setCarId(Long.valueOf(1));
        car.setStatus(true);
        car.setStartingPrice(3000.00);
        car.setBrand("Fiat");
        car.setOffice(office);

        rent.setRentId(Long.valueOf(1));
        rent.setFinalPrice(500.00);
        rent.setCar(car);
        rent.setClient(client);

        rent1.setRentId(Long.valueOf(2));
        rent1.setFinalPrice(5000.00);
        rent1.setCar(car);
        rent1.setClient(client);

    }

    @Test
    void create() throws Exception {
        doReturn(rent).when(rentService).create(any());

        // Execute the POST request
        mockMvc.perform(post("/api/rents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(rent)))

                // Validate the response code and content type
                .andExpect(status().is(201));

    }

    @Test
    void findAll() throws Exception {
        doReturn(Lists.newArrayList(rent, rent1)).when(rentService).findAll();

        // Execute the GET request
        mockMvc.perform(get("/api/rents"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rentId", is(1)))
                .andExpect(jsonPath("$[0].finalPrice", is(500.00)))
                .andExpect(jsonPath("$[0].car.carId", is(1)))
                .andExpect(jsonPath("$[0].car.status", is(true)))
                .andExpect(jsonPath("$[0].car.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$[0].car.brand", is("Fiat")))
                .andExpect( jsonPath("$[0].car.office.officeId", is(1)))
                .andExpect( jsonPath("$[0].car.office.house", is("24")))
                .andExpect( jsonPath("$[0].car.office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$[0].car.office.city", is("Moscow")))
                .andExpect( jsonPath("$[0].car.office.email", is("office@office.ru")))
                .andExpect(jsonPath("$[0].client.clientId", is(1)))
                .andExpect(jsonPath("$[0].client.phone", is("+7(903)555-55-55")))
                .andExpect(jsonPath("$[0].client.passport", is("4515 708432")))
                .andExpect(jsonPath("$[0].client.isBlackListed", is(false)))
                .andExpect(jsonPath("$[0].client.firstName", is("Ivan")))
                .andExpect(jsonPath("$[0].client.lastName", is("Ivanov")))
                .andExpect(jsonPath("$[0].client.driverLicense", is("0583967902386")))
                .andExpect(jsonPath("$[1].rentId", is(2)))
                .andExpect(jsonPath("$[1].finalPrice", is(5000.00)))
                .andExpect(jsonPath("$[1].car.carId", is(1)))
                .andExpect(jsonPath("$[1].car.status", is(true)))
                .andExpect(jsonPath("$[1].car.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$[1].car.brand", is("Fiat")))
                .andExpect( jsonPath("$[1].car.office.officeId", is(1)))
                .andExpect( jsonPath("$[1].car.office.house", is("24")))
                .andExpect( jsonPath("$[1].car.office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$[1].car.office.city", is("Moscow")))
                .andExpect( jsonPath("$[1].car.office.email", is("office@office.ru")))
                .andExpect(jsonPath("$[1].client.clientId", is(1)))
                .andExpect(jsonPath("$[1].client.phone", is("+7(903)555-55-55")))
                .andExpect(jsonPath("$[1].client.passport", is("4515 708432")))
                .andExpect(jsonPath("$[1].client.isBlackListed", is(false)))
                .andExpect(jsonPath("$[1].client.firstName", is("Ivan")))
                .andExpect(jsonPath("$[1].client.lastName", is("Ivanov")))
                .andExpect(jsonPath("$[1].client.driverLicense", is("0583967902386")));
    }

    @Test
    void find() throws Exception {
        doReturn(Optional.of(rent)).when(rentService).find(Long.valueOf(1));

        // Execute the GET request
        mockMvc.perform(get("/api/rents/1"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.rentId", is(1)))
                .andExpect(jsonPath("$.finalPrice", is(500.00)))
                .andExpect(jsonPath("$.car.carId", is(1)))
                .andExpect(jsonPath("$.car.status", is(true)))
                .andExpect(jsonPath("$.car.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$.car.brand", is("Fiat")))
                .andExpect( jsonPath("$.car.office.officeId", is(1)))
                .andExpect( jsonPath("$.car.office.house", is("24")))
                .andExpect( jsonPath("$.car.office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$.car.office.city", is("Moscow")))
                .andExpect( jsonPath("$.car.office.email", is("office@office.ru")))
                .andExpect(jsonPath("$.client.clientId", is(1)))
                .andExpect(jsonPath("$.client.phone", is("+7(903)555-55-55")))
                .andExpect(jsonPath("$.client.passport", is("4515 708432")))
                .andExpect(jsonPath("$.client.isBlackListed", is(false)))
                .andExpect(jsonPath("$.client.firstName", is("Ivan")))
                .andExpect(jsonPath("$.client.lastName", is("Ivanov")))
                .andExpect(jsonPath("$.client.driverLicense", is("0583967902386")));
    }

    @Test
    void updateRent() throws Exception {
        Rent rent2 = rent;
        rent2.setFinalPrice(3500.00);
        doReturn(Optional.of(rent)).when(rentService).find(Long.valueOf(1));
        doReturn(rent2).when(rentService).update(rent);

        // Execute the POST request
        mockMvc.perform(put("/api/rents/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(asJsonString(rent2)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.rentId", is(1)))
                .andExpect(jsonPath("$.finalPrice", is(3500.00)))
                .andExpect(jsonPath("$.car.carId", is(1)))
                .andExpect(jsonPath("$.car.status", is(true)))
                .andExpect(jsonPath("$.car.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$.car.brand", is("Fiat")))
                .andExpect( jsonPath("$.car.office.officeId", is(1)))
                .andExpect( jsonPath("$.car.office.house", is("24")))
                .andExpect( jsonPath("$.car.office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$.car.office.city", is("Moscow")))
                .andExpect( jsonPath("$.car.office.email", is("office@office.ru")))
                .andExpect(jsonPath("$.client.clientId", is(1)))
                .andExpect(jsonPath("$.client.phone", is("+7(903)555-55-55")))
                .andExpect(jsonPath("$.client.passport", is("4515 708432")))
                .andExpect(jsonPath("$.client.isBlackListed", is(false)))
                .andExpect(jsonPath("$.client.firstName", is("Ivan")))
                .andExpect(jsonPath("$.client.lastName", is("Ivanov")))
                .andExpect(jsonPath("$.client.driverLicense", is("0583967902386")));

    }

    @Test
    void deleteRent() throws Exception {
        doReturn(Optional.of(rent)).when(rentService).find(Long.valueOf(1));
        doReturn(true).when(rentService).delete(rent);
        mockMvc.perform(delete("/api/rents/{id}", 1))
                .andExpect(status().isOk());
    }



    @Test
    void findByClientId() throws Exception {
        doReturn(Lists.newArrayList(rent, rent1)).when(rentService).findByClientId(Long.valueOf(1));

        // Execute the GET request
        mockMvc.perform(get("/api/rents/findbyclient/1"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rentId", is(1)))
                .andExpect(jsonPath("$[0].finalPrice", is(500.00)))
                .andExpect(jsonPath("$[0].car.carId", is(1)))
                .andExpect(jsonPath("$[0].car.status", is(true)))
                .andExpect(jsonPath("$[0].car.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$[0].car.brand", is("Fiat")))
                .andExpect( jsonPath("$[0].car.office.officeId", is(1)))
                .andExpect( jsonPath("$[0].car.office.house", is("24")))
                .andExpect( jsonPath("$[0].car.office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$[0].car.office.city", is("Moscow")))
                .andExpect( jsonPath("$[0].car.office.email", is("office@office.ru")))
                .andExpect(jsonPath("$[0].client.clientId", is(1)))
                .andExpect(jsonPath("$[0].client.phone", is("+7(903)555-55-55")))
                .andExpect(jsonPath("$[0].client.passport", is("4515 708432")))
                .andExpect(jsonPath("$[0].client.isBlackListed", is(false)))
                .andExpect(jsonPath("$[0].client.firstName", is("Ivan")))
                .andExpect(jsonPath("$[0].client.lastName", is("Ivanov")))
                .andExpect(jsonPath("$[0].client.driverLicense", is("0583967902386")))
                .andExpect(jsonPath("$[1].rentId", is(2)))
                .andExpect(jsonPath("$[1].finalPrice", is(5000.00)))
                .andExpect(jsonPath("$[1].car.carId", is(1)))
                .andExpect(jsonPath("$[1].car.status", is(true)))
                .andExpect(jsonPath("$[1].car.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$[1].car.brand", is("Fiat")))
                .andExpect( jsonPath("$[1].car.office.officeId", is(1)))
                .andExpect( jsonPath("$[1].car.office.house", is("24")))
                .andExpect( jsonPath("$[1].car.office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$[1].car.office.city", is("Moscow")))
                .andExpect( jsonPath("$[1].car.office.email", is("office@office.ru")))
                .andExpect(jsonPath("$[1].client.clientId", is(1)))
                .andExpect(jsonPath("$[1].client.phone", is("+7(903)555-55-55")))
                .andExpect(jsonPath("$[1].client.passport", is("4515 708432")))
                .andExpect(jsonPath("$[1].client.isBlackListed", is(false)))
                .andExpect(jsonPath("$[1].client.firstName", is("Ivan")))
                .andExpect(jsonPath("$[1].client.lastName", is("Ivanov")))
                .andExpect(jsonPath("$[1].client.driverLicense", is("0583967902386")));
    }

    @Test
    void findByOfficeId() throws Exception {
        doReturn(Lists.newArrayList(rent, rent1)).when(rentService).findByOfficeId(Long.valueOf(1));

        // Execute the GET request
        mockMvc.perform(get("/api/rents/findbyoffice//1"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rentId", is(1)))
                .andExpect(jsonPath("$[0].finalPrice", is(500.00)))
                .andExpect(jsonPath("$[0].car.carId", is(1)))
                .andExpect(jsonPath("$[0].car.status", is(true)))
                .andExpect(jsonPath("$[0].car.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$[0].car.brand", is("Fiat")))
                .andExpect( jsonPath("$[0].car.office.officeId", is(1)))
                .andExpect( jsonPath("$[0].car.office.house", is("24")))
                .andExpect( jsonPath("$[0].car.office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$[0].car.office.city", is("Moscow")))
                .andExpect( jsonPath("$[0].car.office.email", is("office@office.ru")))
                .andExpect(jsonPath("$[0].client.clientId", is(1)))
                .andExpect(jsonPath("$[0].client.phone", is("+7(903)555-55-55")))
                .andExpect(jsonPath("$[0].client.passport", is("4515 708432")))
                .andExpect(jsonPath("$[0].client.isBlackListed", is(false)))
                .andExpect(jsonPath("$[0].client.firstName", is("Ivan")))
                .andExpect(jsonPath("$[0].client.lastName", is("Ivanov")))
                .andExpect(jsonPath("$[0].client.driverLicense", is("0583967902386")))
                .andExpect(jsonPath("$[1].rentId", is(2)))
                .andExpect(jsonPath("$[1].finalPrice", is(5000.00)))
                .andExpect(jsonPath("$[1].car.carId", is(1)))
                .andExpect(jsonPath("$[1].car.status", is(true)))
                .andExpect(jsonPath("$[1].car.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$[1].car.brand", is("Fiat")))
                .andExpect( jsonPath("$[1].car.office.officeId", is(1)))
                .andExpect( jsonPath("$[1].car.office.house", is("24")))
                .andExpect( jsonPath("$[1].car.office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$[1].car.office.city", is("Moscow")))
                .andExpect( jsonPath("$[1].car.office.email", is("office@office.ru")))
                .andExpect(jsonPath("$[1].client.clientId", is(1)))
                .andExpect(jsonPath("$[1].client.phone", is("+7(903)555-55-55")))
                .andExpect(jsonPath("$[1].client.passport", is("4515 708432")))
                .andExpect(jsonPath("$[1].client.isBlackListed", is(false)))
                .andExpect(jsonPath("$[1].client.firstName", is("Ivan")))
                .andExpect(jsonPath("$[1].client.lastName", is("Ivanov")))
                .andExpect(jsonPath("$[1].client.driverLicense", is("0583967902386")));
    }

    @Test
    void findByCar() throws Exception {
        doReturn(Lists.newArrayList(rent, rent1)).when(rentService).findByCarId(Long.valueOf(1));

        // Execute the GET request
        mockMvc.perform(get("/api/rents/findbycar/1"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rentId", is(1)))
                .andExpect(jsonPath("$[0].finalPrice", is(500.00)))
                .andExpect(jsonPath("$[0].car.carId", is(1)))
                .andExpect(jsonPath("$[0].car.status", is(true)))
                .andExpect(jsonPath("$[0].car.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$[0].car.brand", is("Fiat")))
                .andExpect( jsonPath("$[0].car.office.officeId", is(1)))
                .andExpect( jsonPath("$[0].car.office.house", is("24")))
                .andExpect( jsonPath("$[0].car.office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$[0].car.office.city", is("Moscow")))
                .andExpect( jsonPath("$[0].car.office.email", is("office@office.ru")))
                .andExpect(jsonPath("$[0].client.clientId", is(1)))
                .andExpect(jsonPath("$[0].client.phone", is("+7(903)555-55-55")))
                .andExpect(jsonPath("$[0].client.passport", is("4515 708432")))
                .andExpect(jsonPath("$[0].client.isBlackListed", is(false)))
                .andExpect(jsonPath("$[0].client.firstName", is("Ivan")))
                .andExpect(jsonPath("$[0].client.lastName", is("Ivanov")))
                .andExpect(jsonPath("$[0].client.driverLicense", is("0583967902386")))
                .andExpect(jsonPath("$[1].rentId", is(2)))
                .andExpect(jsonPath("$[1].finalPrice", is(5000.00)))
                .andExpect(jsonPath("$[1].car.carId", is(1)))
                .andExpect(jsonPath("$[1].car.status", is(true)))
                .andExpect(jsonPath("$[1].car.startingPrice", is(3000.00)))
                .andExpect(jsonPath("$[1].car.brand", is("Fiat")))
                .andExpect( jsonPath("$[1].car.office.officeId", is(1)))
                .andExpect( jsonPath("$[1].car.office.house", is("24")))
                .andExpect( jsonPath("$[1].car.office.street", is("Leningradskiy Avenue")))
                .andExpect( jsonPath("$[1].car.office.city", is("Moscow")))
                .andExpect( jsonPath("$[1].car.office.email", is("office@office.ru")))
                .andExpect(jsonPath("$[1].client.clientId", is(1)))
                .andExpect(jsonPath("$[1].client.phone", is("+7(903)555-55-55")))
                .andExpect(jsonPath("$[1].client.passport", is("4515 708432")))
                .andExpect(jsonPath("$[1].client.isBlackListed", is(false)))
                .andExpect(jsonPath("$[1].client.firstName", is("Ivan")))
                .andExpect(jsonPath("$[1].client.lastName", is("Ivanov")))
                .andExpect(jsonPath("$[1].client.driverLicense", is("0583967902386")));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}