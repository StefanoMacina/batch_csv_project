package com.app.dashboard_backend.mapper;


import com.app.dashboard_backend.models.Order;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


@Configuration
public class Mapper {


    public static LineMapper<Order> lineMapper() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        DefaultLineMapper<Order> lineMapper = new DefaultLineMapper<>();
        BeanWrapperFieldSetMapper<Order> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);

        lineTokenizer.setNames("id","product_name","quantity","price","customer_name","customer_email");

        fieldSetMapper.setTargetType(Order.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }
}
