package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Insurance;
import com.AnnaMarunko.CarRentDesktop.repos.InsuranceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Insurance service.
 */
@Service
public class InsuranceService {

    @Autowired
    private InsuranceRepo insuranceRepo;

    /**
     * Create.
     *
     * @param insurance the insurance
     */
    public Insurance create(Insurance insurance){
        return insuranceRepo.save(insurance);
    }

    /**
     * Update.
     *
     * @param insurance the insurance
     */
    public Insurance update(Insurance insurance) {return insuranceRepo.save(insurance); }

    /**
     * Delete.
     *
     * @param insurance the insurance
     */
    public Boolean delete(Insurance insurance) { insuranceRepo.delete(insurance);
    return true;}

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Insurance> findAll(){
        return insuranceRepo.findAll();
    }

    /**
     * Find optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Insurance> find(Long id){
        return insuranceRepo.findById(id);
    }

    /**
     * Find by car id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Insurance> findByCarId(Long id){
        return insuranceRepo.findByCar_CarId(id);
    }

}
