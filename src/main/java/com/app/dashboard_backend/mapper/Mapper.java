package com.app.dashboard_backend.mapper;


import com.app.dashboard_backend.getSrc.GetSrc;
import com.app.dashboard_backend.models.Order;
import com.app.dashboard_backend.services.OrderRepositoryImpl;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.nio.file.FileSystem;

@Configuration
public class Mapper {
    private static String delimiter = ",";
    @Autowired
    static OrderRepositoryImpl orderRepositoryImpl;


    public static LineMapper<Order> lineMapper() {

        try{
            DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
            DefaultLineMapper<Order> lineMapper = new DefaultLineMapper<>();
            BeanWrapperFieldSetMapper<Order> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

            BufferedReader br = new BufferedReader(new FileReader(GetSrc.srcPath()));

            String [] headerList =  br.readLine().split(delimiter);
            System.out.print(headerList);
            br.close();

            lineTokenizer.setNames(headerList);
            lineTokenizer.setDelimiter(delimiter);
            lineTokenizer.setStrict(false);

            fieldSetMapper.setTargetType(Order.class);

            lineMapper.setLineTokenizer(lineTokenizer);
            lineMapper.setFieldSetMapper(fieldSetMapper);
            return lineMapper;

            } catch (IOException e){
                e.printStackTrace();
                return null;
            }

    }

}

