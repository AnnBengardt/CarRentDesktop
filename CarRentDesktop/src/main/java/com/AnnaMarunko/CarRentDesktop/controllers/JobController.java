package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Job;
import com.AnnaMarunko.CarRentDesktop.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The type Job controller.
 */
@RestController
public class JobController {

    private final JobService jobService;

    /**
     * Instantiates a new Job controller.
     *
     * @param jobService the job service
     */
    @Autowired
    public JobController(JobService jobService){
        this.jobService = jobService;
    }

    /**
     * Create response entity.
     *
     * @param job the job
     * @return the response entity
     */
    @PostMapping("/api/jobs")
    public ResponseEntity<?> create(@RequestBody Job job){
        jobService.create(job);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping("/api/jobs")
    public ResponseEntity<List<Job>> findAll(){
        final List<Job> jobList = jobService.findAll();
        return jobList != null && !jobList.isEmpty()
                ? new ResponseEntity<>(jobList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Find response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/api/jobs/{id}")
    public ResponseEntity<Optional<Job>> find(@PathVariable(name = "id") Long id){
        final Optional<Job> job = jobService.find(id);
        return job != null
                ? new ResponseEntity<>(job, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * Update job response entity.
     *
     * @param id        the id
     * @param jobUpdate the job update
     * @return the response entity
     */
    @PutMapping("/api/jobs/{id}")
    public ResponseEntity<?> updateJob(@PathVariable(name = "id") Long id, @RequestBody Job jobUpdate) {
        return jobService.find(id).map(job -> {
            job.setJobName(jobUpdate.getJobName());
            jobService.update(job);
            return new ResponseEntity<>(job, HttpStatus.OK);
        }).orElseThrow(() -> new IllegalArgumentException());

    }

    /**
     * Delete job response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/api/jobs/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable(name = "id") Long id) {
        return jobService.find(id).map(job -> {
            jobService.delete(job);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException());
    }

}
