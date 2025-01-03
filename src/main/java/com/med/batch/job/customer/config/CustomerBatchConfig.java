package com.med.batch.job.customer.config;

import com.med.batch.core.processor.IBatchItemProcessor;
import com.med.batch.core.reader.IBatchItemReader;
import com.med.batch.core.writer.interfaces.IItemStreamItemWriter;
import com.med.batch.job.customer.listener.CustomerListener;
import com.med.batch.job.customer.listener.StepErrorListener;
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

import java.io.IOException;

@RequiredArgsConstructor
@Configuration
public class CustomerBatchConfig {
    private static final String CUSTOMER_STEP_NAME = "customerStep1";
    private static final String CUSTOMER_JOB_NAME = "customerJob1";

    private static final int CHUNK = 10;
    private static final int SKIP_LIMIT = 5;
    private static final int RETRY_LIMIT = 5;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final IBatchItemReader<Customer> customerReader;
    private final IBatchItemProcessor<Customer, Customer> customerProcessor;
    private final IItemStreamItemWriter<Customer> customerFlatFileWriter;


    @Bean
    public Step customerStep1() {
        return new StepBuilder(CUSTOMER_STEP_NAME, jobRepository)
                .<Customer, Customer>chunk(CHUNK, transactionManager)
                .reader(customerReader.createReader(new FileSystemResource("src/main/resources/mini-file.txt")))
                .processor(customerProcessor)
                .writer(customerFlatFileWriter)
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(SKIP_LIMIT)
                .listener(new CustomerListener())
                .listener(new StepErrorListener())
                .build();
    }

    @Bean
    public Job customerJob() throws IOException {
        return new JobBuilder(CUSTOMER_JOB_NAME, jobRepository)
                .start(customerStep1())
                .build();
    }
}