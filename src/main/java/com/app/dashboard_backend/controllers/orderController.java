package com.app.dashboard_backend.controllers;

import com.app.dashboard_backend.models.Order;
import com.app.dashboard_backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController @RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class orderController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Autowired
    OrderService orderService;

    @PostMapping("/send")
    public String importCsvToDb(){

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt",System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return "Job launched successfully";
    }

    @GetMapping("/getall")
    public Map<String, Object> getAllUsers() {
        return orderService.getAll();
    }

    @GetMapping("/getall/{id}")
    public Order getOneById(@PathVariable("id")int petId){
        return orderService.getOrderById(petId);
    }









}
