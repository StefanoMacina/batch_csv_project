package com.app.dashboard_backend.controllers;

import com.app.dashboard_backend.models.Order;
import com.app.dashboard_backend.services.OrderRepositoryImpl;
import com.app.dashboard_backend.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    @Autowired
    OrderRepositoryImpl orderRepositoryImpl;

    @PostMapping(value = "/send",
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<String> importCsvToDb(MultipartFile file){
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt",System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);


            FileWriter writer = new FileWriter("src/main/resources/batch_input/test.csv");
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            writer.write(content);
            writer.close();

            return ResponseEntity.ok("success");
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
