package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Rent;
import com.AnnaMarunko.CarRentDesktop.repos.RentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentService {

    @Autowired
    private RentRepo rentRepo;

    public void create(Rent rent){
        rentRepo.save(rent);
    }

    public void update(Rent rent) { rentRepo.save(rent); }

    public void delete(Rent rent) { rentRepo.delete(rent); }

    public List<Rent> findAll(){
        return rentRepo.findAll();
    }

    public Optional<Rent> find(Long id){
        return rentRepo.findById(id);
    }

    public Optional<Rent> findByClientId(Long id){
        return rentRepo.findByClient_ClientId(id);
    }

    public List<Rent> findByOfficeId(Long id) {return rentRepo.findByCar_Office_OfficeId(id);}
}
