package com.AnnaMarunko.CarRentDesktop.repos;

import com.AnnaMarunko.CarRentDesktop.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepo extends JpaRepository<Job, Long> {
}
