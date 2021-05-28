package com.AnnaMarunko.CarRentDesktop.controllers;

import com.AnnaMarunko.CarRentDesktop.entities.Job;
import com.AnnaMarunko.CarRentDesktop.services.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class JobControllerTest {

    @MockBean
    private JobService jobService;

    @Autowired
    private MockMvc mockMvc;

    Job job = new Job();
    Job job1 = new Job();

    @BeforeEach
    void setUp() {

        job.setJobName("Mechanic");
        job.setJobId(Long.valueOf(1));

        job1.setJobName("Manager");
        job1.setJobId(Long.valueOf(2));

    }

    @Test
    void create() throws Exception {
        doReturn(job).when(jobService).create(any());

        // Execute the POST request
        mockMvc.perform(post("/api/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(job)))

                // Validate the response code and content type
                .andExpect(status().is(201));

    }

    @Test
    void findAll() throws Exception {
        doReturn(Lists.newArrayList(job, job1)).when(jobService).findAll();

        // Execute the GET request
        mockMvc.perform(get("/api/jobs"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].jobId", is(1)))
                .andExpect(jsonPath("$[0].jobName", is("Mechanic")))
                .andExpect(jsonPath("$[1].jobId", is(2)))
                .andExpect(jsonPath("$[1].jobName", is("Manager")));
    }

    @Test
    void find() throws Exception {
        doReturn(Optional.of(job)).when(jobService).find(Long.valueOf(1));

        // Execute the GET request
        mockMvc.perform(get("/api/jobs/1"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.jobId", is(1)))
                .andExpect(jsonPath("$.jobName", is("Mechanic")));
    }

    @Test
    void updateJob() throws Exception {
        Job job2 = job;
        job2.setJobName("System admin");
        doReturn(Optional.of(job)).when(jobService).find(Long.valueOf(1));
        doReturn(job2).when(jobService).update(job);

        // Execute the POST request
        mockMvc.perform(put("/api/jobs/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 2)
                .content(asJsonString(job2)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.jobId", is(1)))
                .andExpect(jsonPath("$.jobName", is("System admin")));
    }

    @Test
    void deleteJob() throws Exception {
        doReturn(Optional.of(job)).when(jobService).find(Long.valueOf(1));
        doReturn(true).when(jobService).delete(job);
        mockMvc.perform(delete("/api/jobs/{id}", 1))

                .andExpect(status().isOk());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}