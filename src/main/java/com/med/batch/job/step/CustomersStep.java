package com.med.batch.job.step;

import com.med.batch.model.Customer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@AllArgsConstructor
public class CustomersStep {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ItemReader<Customer> customerItemReader;
    private final ItemProcessor<Customer, Customer> customerProcessor;
    private final ItemWriter<Customer> customerItemWriter;


    @Bean
    public Step customerStep() {
        log.info("Initializing Step: STEP-READ-WRITE-CUSTOMER");
        return new StepBuilder("STEP-READ-WRITE-CUSTOMER", jobRepository)
                .<Customer, Customer>chunk(10, transactionManager)
                .reader(customerItemReader)
                .processor(customerProcessor)
                .writer(customerItemWriter)
                .build();
    }
}
