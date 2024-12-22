package com.med.batch.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class CustomersJob {
    private final JobRepository jobRepository;
    private final Step customerStep;


    @Bean
    public Job customerJob() {
        log.info("Building Job: JOB-READ-WRITE-CUSTOMER");
        return new JobBuilder("JOB-READ-WRITE-CUSTOMER", jobRepository)
                .start(customerStep)
                .build();
    }
}
