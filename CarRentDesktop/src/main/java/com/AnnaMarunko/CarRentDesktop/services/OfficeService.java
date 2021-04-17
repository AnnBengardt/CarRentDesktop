package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Office;
import com.AnnaMarunko.CarRentDesktop.repos.OfficeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfficeService {

    @Autowired
    private OfficeRepo officeRepo;

    public void create(Office office){
        officeRepo.save(office);
    }

    public void update(Office office) { officeRepo.save(office); }

    public void delete(Office office) { officeRepo.delete(office); }

    public List<Office> findAll(){
        return officeRepo.findAll();
    }

    public Optional<Office> find(Long id){
        return officeRepo.findById(id);
    }

}
