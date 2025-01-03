package com.med.batch.core.listener;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import java.util.List;

@Slf4j
public abstract class AbstractStepExecutionListener implements StepExecutionListener {
    protected void doBeforeStep(StepExecution stepExecution) {
    }

    protected void doAfterStep(StepExecution stepExecution) {
    }

    @Override
    public void beforeStep(@NonNull StepExecution stepExecution) {
        log.info("Step execution started at {}", stepExecution.getStartTime());
        doBeforeStep(stepExecution);
    }

    @Override
    public ExitStatus afterStep(@NonNull StepExecution stepExecution) {
        log.info("Step execution finished with status: {} at {}", stepExecution.getStatus(), stepExecution.getEndTime());
        log.warn("skipped count {}", stepExecution.getSkipCount());
        List<Throwable> throwableList = stepExecution.getFailureExceptions();
        if (!throwableList.isEmpty()) {
            log.error("Step execution failed with exceptions: ");
            for (Throwable t : stepExecution.getFailureExceptions()) {
                log.error(t.getMessage());
            }
        }
        doAfterStep(stepExecution);

        return stepExecution.getExitStatus();
    }
}
