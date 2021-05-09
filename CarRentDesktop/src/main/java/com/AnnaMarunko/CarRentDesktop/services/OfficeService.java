package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Office;
import com.AnnaMarunko.CarRentDesktop.repos.OfficeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Office service.
 */
@Service
public class OfficeService {

    @Autowired
    private OfficeRepo officeRepo;

    /**
     * Create.
     *
     * @param office the office
     */
    public void create(Office office){
        officeRepo.save(office);
    }

    /**
     * Update.
     *
     * @param office the office
     */
    public void update(Office office) { officeRepo.save(office); }

    /**
     * Delete.
     *
     * @param office the office
     */
    public void delete(Office office) { officeRepo.delete(office); }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Office> findAll(){
        return officeRepo.findAll();
    }

    /**
     * Find optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Office> find(Long id){
        return officeRepo.findById(id);
    }

}
