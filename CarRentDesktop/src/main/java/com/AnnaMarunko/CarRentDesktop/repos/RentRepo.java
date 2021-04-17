package com.AnnaMarunko.CarRentDesktop.repos;

import com.AnnaMarunko.CarRentDesktop.entities.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentRepo extends JpaRepository<Rent, Long> {
    public Optional<Rent> findByClient_ClientId(Long clientId);
}
