package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Job;
import com.AnnaMarunko.CarRentDesktop.repos.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Job service.
 */
@Service
public class JobService {

    @Autowired
    private JobRepo jobRepo;

    /**
     * Create.
     *
     * @param job the job
     */
    public void create(Job job){
        jobRepo.save(job);
    }

    /**
     * Update.
     *
     * @param job the job
     */
    public void update(Job job) { jobRepo.save(job); }

    /**
     * Delete.
     *
     * @param job the job
     */
    public void delete(Job job) { jobRepo.delete(job); }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Job> findAll(){
        return jobRepo.findAll();
    }

    /**
     * Find optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Job> find(Long id){
        return jobRepo.findById(id);
    }

}
