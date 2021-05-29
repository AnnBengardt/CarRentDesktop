package com.AnnaMarunko.CarRentDesktop.services;

import com.AnnaMarunko.CarRentDesktop.entities.Job;
import com.AnnaMarunko.CarRentDesktop.repos.JobRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class JobServiceTest {

    @Autowired
    JobService jobService;

    @MockBean
    JobRepo jobRepo;

    Job job = new Job();
    Job job1 = new Job();

    @BeforeEach
    void setUp() {
        job.setJobName("Mechanic");
        job.setJobId(Long.valueOf(1));

        job1.setJobName("Manager");
        job.setJobId(Long.valueOf(2));

    }

    @Test
    @DisplayName("Create a job")
    void create() {
        doReturn(job1).when(jobRepo).save(job1);

        // Execute the service call
        Job returnedValue = jobService.create(job1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved job should not be null");
    }

    @Test
    @DisplayName("Update a job")
    void update() {
        job1.setJobName("Sales manager");
        doReturn(job1).when(jobRepo).save(job1);

        // Execute the service call
        Job returnedValue = jobService.update(job1);

        // Assert the response
        Assertions.assertNotNull(returnedValue, "The saved job should not be null");
        Assertions.assertSame(job1, returnedValue, "The job returned was not the same as the mock");
    }

    @Test
    @DisplayName("Delete a job")
    void delete() {
        doNothing().when(jobRepo).delete(job1);

        // Execute the service call
        Boolean returnedValue = jobService.delete(job1);

        // Assert the response
        Assertions.assertEquals(true, returnedValue, "The deleted job should return true");


    }

    @Test
    @DisplayName("Find all jobs")
    void findAll() {
        doReturn(Arrays.asList(job, job1)).when(jobRepo).findAll();

        // Execute the service call
        List<Job> jobs = jobService.findAll();

        // Assert the response
        Assertions.assertEquals(2, jobs.size(), "findAll should return 2 jobs!");
    }

    @Test
    @DisplayName("Find a job by ID")
    void find() {

        doReturn(Optional.of(job)).when(jobRepo).findById(Long.valueOf(1));

        // Execute the service call
        Optional<Job> returnedValue = jobService.find(Long.valueOf(1));

        // Assert the response
        Assertions.assertTrue(returnedValue.isPresent(), "Job was not found");
        Assertions.assertSame(job, returnedValue.get(), "The job returned was not the same as the mock");

    }

    @Test
    @DisplayName("Find a job by ID (fail)")
    void findNotFound() {
        doReturn(Optional.empty()).when(jobRepo).findById(Long.valueOf(5));

        // Execute the service call
        Optional<Job> returnedValue = jobService.find(Long.valueOf(5));

        // Assert the response
        Assertions.assertFalse(returnedValue.isPresent(), "Job should not be found");
    }
}