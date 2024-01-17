package com.app.dashboard_backend.config;


import com.app.dashboard_backend.mapper.Mapper;
import com.app.dashboard_backend.models.Order;
import com.app.dashboard_backend.processor.OrderProcessor;
import com.app.dashboard_backend.repository.IOrderRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Objects;

@Configuration
public class BatchConfig{

    @Autowired
    JobRepository jobRepository;
    @Autowired
    IOrderRepository orderRepository;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    @Bean
    public FlatFileItemReader<Order> orderReader(){
        FlatFileItemReader<Order> reader = new FlatFileItemReader<>();
        reader.setName("order_csv_reader");
        reader.setLinesToSkip(1);
        reader.setLineMapper(Mapper.lineMapper());
        reader.setResource(new FileSystemResource("src/main/resources/orders.csv"));

        return reader;
    }


    @Bean
    public OrderProcessor process(){
        return new OrderProcessor();
    }

    @Bean
    public RepositoryItemWriter<Order> orderWriter(){
        RepositoryItemWriter<Order> writer = new RepositoryItemWriter<>();
            writer.setRepository(orderRepository);
            writer.setMethodName("save");
            return writer;
    }

    @Bean
    public Step orderStep() {
        return new StepBuilder("orderStep", jobRepository)
                .<Order, Order>chunk(1000, platformTransactionManager)
                .reader(orderReader())
                .processor(process())
                .writer(orderWriter())
                .build();
    }

    @Bean
    public Job fromCsvToDbJob(){
        return new JobBuilder("fromCsvToDbJob",jobRepository)
                .start(orderStep())
                .build();
    }

}