package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Job;
import com.AnnaMarunko.CarRentDesktop.repos.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepo jobRepo;

    public void create(Job job){
        jobRepo.save(job);
    }

    public void update(Job job) { jobRepo.save(job); }

    public void delete(Job job) { jobRepo.delete(job); }

    public List<Job> findAll(){
        return jobRepo.findAll();
    }

    public Optional<Job> find(Long id){
        return jobRepo.findById(id);
    }

}
