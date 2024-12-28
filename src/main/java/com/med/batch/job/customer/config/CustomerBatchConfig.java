package com.med.batch.job.customer.config;

import com.med.batch.core.processor.IBatchItemProcessor;
import com.med.batch.core.reader.IBatchItemReader;
import com.med.batch.core.writer.IBatchItemWriter;
import com.med.batch.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class CustomerBatchConfig {
    private static final String CUSTOMER_STEP_NAME = "customerStep1";
    private static final String CUSTOMER_JOB_NAME = "customerJob1";

    private static final int CHUNK = 10;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final IBatchItemReader<Customer> reader;
    private final IBatchItemProcessor<Customer, Customer> processor;
    private final IBatchItemWriter<Customer> writer;


    @Bean
    public Step customerStep1() {
        return new StepBuilder(CUSTOMER_STEP_NAME, jobRepository)
                .<Customer, Customer>chunk(CHUNK, transactionManager)
                .reader(reader.createReader(new FileSystemResource("src/main/resources/customers-data.txt")))
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job customerJob() {
        return new JobBuilder(CUSTOMER_JOB_NAME, jobRepository)
                .start(customerStep1())
                .build();
    }
}