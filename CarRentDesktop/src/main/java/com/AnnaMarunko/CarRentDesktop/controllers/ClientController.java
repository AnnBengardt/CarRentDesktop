package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Client;
import com.AnnaMarunko.CarRentDesktop.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The type Client controller.
 */
@RestController
public class ClientController {

    private final ClientService clientService;

    /**
     * Instantiates a new Client controller.
     *
     * @param clientService the client service
     */
    @Autowired
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    /**
     * Create response entity.
     *
     * @param client the client
     * @return the response entity
     */
    @PostMapping("/api/clients")
    public ResponseEntity<?> create(@RequestBody Client client){
        clientService.create(client);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping("/api/clients")
    public ResponseEntity<List<Client>> findAll(){
        final List<Client> clientList = clientService.findAll();
        return clientList != null && !clientList.isEmpty()
                ? new ResponseEntity<>(clientList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Find response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/api/clients/{id}")
    public ResponseEntity<Optional<Client>> find(@PathVariable(name = "id") Long id){
        final Optional<Client> client = clientService.find(id);
        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * Update client response entity.
     *
     * @param id           the id
     * @param clientUpdate the client update
     * @return the response entity
     */
    @PutMapping("/api/clients/{id}")
    public ResponseEntity<?> updateClient(@PathVariable(name = "id") Long id, @RequestBody Client clientUpdate) {
        return clientService.find(id).map(client -> {
            client.setFirstName(clientUpdate.getFirstName());
            client.setLastName(clientUpdate.getLastName());
            client.setDriverLicense(clientUpdate.getDriverLicense());
            client.setPassport(clientUpdate.getPassport());
            client.setPhone(clientUpdate.getPhone());
            client.setIsBlackListed(clientUpdate.getIsBlackListed());
            clientService.update(client);
            return new ResponseEntity<>(client, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    /**
     * Delete client response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/api/clients/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable(name = "id") Long id) {
        return clientService.find(id).map(client -> {
            clientService.delete(client);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }
}
