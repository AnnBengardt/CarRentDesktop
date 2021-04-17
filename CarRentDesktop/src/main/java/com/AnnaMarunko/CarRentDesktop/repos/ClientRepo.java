package com.AnnaMarunko.CarRentDesktop.repos;

import com.AnnaMarunko.CarRentDesktop.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
}
