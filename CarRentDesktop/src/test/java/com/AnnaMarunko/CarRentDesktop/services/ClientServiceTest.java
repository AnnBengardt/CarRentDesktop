package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Client;
import com.AnnaMarunko.CarRentDesktop.entities.Employee;
import com.AnnaMarunko.CarRentDesktop.repos.ClientRepo;
import com.AnnaMarunko.CarRentDesktop.repos.EmployeeRepo;
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
class ClientServiceTest {

    @Autowired
    ClientService clientService;

    @MockBean
    ClientRepo clientRepo;

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
    void create() {
        doReturn(client1).when(clientRepo).save(client1);

        // Execute the service call
        Client returnedValue = clientService.create(client1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved client should not be null");
    }

    @Test
    void update() {
        client1.setIsBlackListed(true);
        doReturn(client1).when(clientRepo).save(client1);

        // Execute the service call
        Client returnedValue = clientService.create(client1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved client should not be null");
        Assertions.assertSame(client1, returnedValue, "The client returned was not the same as the mock");
    }

    @Test
    void delete() {
        doNothing().when(clientRepo).delete(client1);

        // Execute the service call
        Boolean returnedValue = clientService.delete(client1);

        // Assert the response
        Assertions.assertEquals(true, returnedValue, "The deleted client should return true");


    }

    @Test
    void findAll() {
        doReturn(Arrays.asList(client, client1)).when(clientRepo).findAll();

        // Execute the service call
        List<Client> clients = clientService.findAll();

        // Assert the response
        Assertions.assertEquals(2, clients.size(), "findAll should return 2 clients!");
    }

    @Test
    void find() {

        doReturn(Optional.of(client)).when(clientRepo).findById(Long.valueOf(1));

        // Execute the service call
        Optional<Client> returnedValue = clientService.find(Long.valueOf(1));

        // Assert the response
        Assertions.assertTrue(returnedValue.isPresent(), "Client was not found");
        Assertions.assertSame(client, returnedValue.get(), "The client returned was not the same as the mock");

    }

    @Test
    void findNotFound() {
        doReturn(Optional.empty()).when(clientRepo).findById(Long.valueOf(5));

        // Execute the service call
        Optional<Client> returnedValue = clientService.find(Long.valueOf(5));

        // Assert the response
        Assertions.assertFalse(returnedValue.isPresent(), "Client should not be found");
    }
}