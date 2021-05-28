package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Client;
import com.AnnaMarunko.CarRentDesktop.repos.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Client service.
 */
@Service
public class ClientService {

    @Autowired
    private ClientRepo clientRepo;

    /**
     * Create.
     *
     * @param client the client
     */
    public Client create(Client client){
        return clientRepo.save(client);
    }

    /**
     * Update.
     *
     * @param client the client
     */
    public Client update(Client client) { return clientRepo.save(client); }

    /**
     * Delete.
     *
     * @param client the client
     */
    public Boolean delete(Client client) { clientRepo.delete(client);
    return true;}

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Client> findAll(){
        return clientRepo.findAll();
    }

    /**
     * Find optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Client> find(Long id){
        return clientRepo.findById(id);
    }

}
