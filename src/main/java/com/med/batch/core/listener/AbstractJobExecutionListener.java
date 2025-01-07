package com.med.batch.core.listener;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;

@Slf4j
public class AbstractJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(@NonNull JobExecution jobExecution) {
        log.info("Start of the parallel flows");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("end of the parallel flows");
        log.info("Status: {}", jobExecution.getStatus());
        // Give the status of each flow
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            log.info("Step {} status: {}", stepExecution.getStepName(), stepExecution.getStatus());
        }
    }
}
