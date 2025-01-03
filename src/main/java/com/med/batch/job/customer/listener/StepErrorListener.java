package com.med.batch.job.customer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StepErrorListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Step execution started at {} for step : {}", stepExecution.getStartTime(), stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("step finished at {} for step : {}", stepExecution.getStartTime(), stepExecution.getStepName());
        log.error("skip count : {}", stepExecution.getSkipCount());
        return stepExecution.getExitStatus();
    }


}
