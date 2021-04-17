package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Insurance;
import com.AnnaMarunko.CarRentDesktop.repos.InsuranceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InsuranceService {

    @Autowired
    private InsuranceRepo insuranceRepo;

    public void create(Insurance insurance){
        insuranceRepo.save(insurance);
    }

    public void update(Insurance insurance) { insuranceRepo.save(insurance); }

    public void delete(Insurance insurance) { insuranceRepo.delete(insurance); }

    public List<Insurance> findAll(){
        return insuranceRepo.findAll();
    }

    public Optional<Insurance> find(Long id){
        return insuranceRepo.findById(id);
    }

    public Optional<Insurance> findByCarId(Long id){
        return insuranceRepo.findByCar_CarId(id);
    }

}
