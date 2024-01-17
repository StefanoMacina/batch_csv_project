package com.app.dashboard_backend.config;


import com.app.dashboard_backend.models.Order;
import com.app.dashboard_backend.processor.OrderProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class BatchConfig{

    @Bean
    public FlatFileItemReader orderReader(){
        FlatFileItemReader<Order> reader = new FlatFileItemReader<>();
        reader.setName("order_csv_reader");
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper);
        reader.setResource(new FileSystemResource("/src/main/resources/orders.csv"));

        return reader;
    }


    @Bean
    public OrderProcessor process(){
        return new OrderProcessor();
    }


    private LineMapper<Order> lineMapper {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("order_id","");
    }




}