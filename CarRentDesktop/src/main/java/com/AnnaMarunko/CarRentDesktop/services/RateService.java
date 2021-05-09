package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Rate;
import com.AnnaMarunko.CarRentDesktop.repos.RateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Rate service.
 */
@Service
public class RateService {

    @Autowired
    private RateRepo rateRepo;

    /**
     * Create.
     *
     * @param rate the rate
     */
    public void create(Rate rate){
        rateRepo.save(rate);
    }

    /**
     * Update.
     *
     * @param rate the rate
     */
    public void update(Rate rate) { rateRepo.save(rate); }

    /**
     * Delete.
     *
     * @param rate the rate
     */
    public void delete(Rate rate) { rateRepo.delete(rate); }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Rate> findAll(){
        return rateRepo.findAll();
    }

    /**
     * Find optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Rate> find(Long id){
        return rateRepo.findById(id);
    }

}