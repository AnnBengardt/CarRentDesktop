package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Rate;
import com.AnnaMarunko.CarRentDesktop.repos.RateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RateService {

    @Autowired
    private RateRepo rateRepo;

    public void create(Rate rate){
        rateRepo.save(rate);
    }

    public void update(Rate rate) { rateRepo.save(rate); }

    public void delete(Rate rate) { rateRepo.delete(rate); }

    public List<Rate> findAll(){
        return rateRepo.findAll();
    }

    public Optional<Rate> find(Long id){
        return rateRepo.findById(id);
    }

}