package com.app.dashboard_backend.config;


import com.app.dashboard_backend.getSrc.GetSrc;
import com.app.dashboard_backend.mapper.Mapper;
import com.app.dashboard_backend.models.Order;
import com.app.dashboard_backend.processor.OrderProcessor;
import com.app.dashboard_backend.services.OrderRepositoryImpl;
import com.app.dashboard_backend.services.repository.IOrderRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig{

    @Autowired
    OrderRepositoryImpl orderRepositoryImpl;
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

        reader.setResource(new FileSystemResource(GetSrc.srcPath()));
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
                .<Order, Order>chunk(2000, platformTransactionManager)
                .reader(orderReader())
                .processor(process())
                .writer(orderWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
            taskExecutor.setConcurrencyLimit(10);
            return taskExecutor;

    }

    @Bean
    public Job fromCsvToDbJob(){
        return new JobBuilder("fromCsvToDbJob",jobRepository)
                .start(orderStep())
                .build();
    }

}