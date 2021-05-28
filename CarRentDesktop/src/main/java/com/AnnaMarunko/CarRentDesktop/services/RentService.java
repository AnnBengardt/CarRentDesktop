package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Rent;
import com.AnnaMarunko.CarRentDesktop.repos.RentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Rent service.
 */
@Service
public class RentService {

    @Autowired
    private RentRepo rentRepo;

    /**
     * Create.
     *
     * @param rent the rent
     */
    public Rent create(Rent rent){
        return rentRepo.save(rent);
    }

    /**
     * Update.
     *
     * @param rent the rent
     */
    public Rent update(Rent rent) { return rentRepo.save(rent); }

    /**
     * Delete.
     *
     * @param rent the rent
     */
    public Boolean delete(Rent rent) { rentRepo.delete(rent);
    return true;}

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Rent> findAll(){
        return rentRepo.findAll();
    }

    /**
     * Find optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Rent> find(Long id){
        return rentRepo.findById(id);
    }

    /**
     * Find by client id optional.
     *
     * @param id the id
     * @return the optional
     */
    public List<Rent> findByClientId(Long id){
        return rentRepo.findByClient_ClientId(id);
    }

    /**
     * Find by office id list.
     *
     * @param id the id
     * @return the list
     */
    public List<Rent> findByOfficeId(Long id) {return rentRepo.findByCar_Office_OfficeId(id);}

    /**
     * Find by car id list.
     *
     * @param id the id
     * @return the list
     */
    public List<Rent> findByCarId(Long id) {return rentRepo.findByCar_CarId(id);}
}
