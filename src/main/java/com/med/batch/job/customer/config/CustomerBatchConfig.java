package com.med.batch.job.customer.config;

import com.med.batch.core.processor.IBatchItemProcessor;
import com.med.batch.core.reader.IBatchItemReader;
import com.med.batch.core.writer.interfaces.IItemStreamItemWriter;
import com.med.batch.exception.LineNotFormated;
import com.med.batch.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
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
    private static final int SKIP_LIMIT = 5;
    private static final int RETRY_LIMIT = 5;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final IBatchItemReader<Customer> customerReader;
    private final IBatchItemProcessor<Customer, Customer> customerProcessor;
    private final IItemStreamItemWriter<Customer> customerFlatFileWriter;

    private final SkipListener<Customer, Customer> customerSkipListener;
    private final StepExecutionListener stepExecutionListener;

    @Value("${batch.resource.s1}")
    private String customerSource1;
    @Value("${batch.resource.s2}")
    private String customerSource2;


    @Bean
    public Step customerStep1() {
        return new StepBuilder(CUSTOMER_STEP_NAME, jobRepository)
                .<Customer, Customer>chunk(CHUNK, transactionManager)
                .reader(customerReader.createReader(new FileSystemResource(customerSource1)))
                .processor(customerProcessor)
                .writer(customerFlatFileWriter)
                .faultTolerant()
                .skip(LineNotFormated.class)
                .skipLimit(SKIP_LIMIT)
                .listener(customerSkipListener)
                .listener(stepExecutionListener)
                .build();

        /*
        => note : be attention to order of listener ex.
        .listener(customerSkipListener)
        .listener(stepExecutionListener) // working
        But
        .listener(stepExecutionListener)
        .listener(customerSkipListener) // not working

        * */
    }

    @Bean
    public Job customerJob() {
        return new JobBuilder(CUSTOMER_JOB_NAME, jobRepository)
                .start(customerStep1())
                .build();
    }
}