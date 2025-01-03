package com.med.batch.job.customer.listener;

import com.med.batch.core.listener.AbstractSkipListener;
import com.med.batch.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerSkipListener extends AbstractSkipListener<Customer, Customer> {

    @Override
    protected void doOnSkipInRead(Throwable t) {
        log.info("CustomerSkipListener onSkipInRead");
        log.warn("skipping for raised exception : {}", t.getMessage());
    }

    @Override
    protected void doOnSkipInProcess(Customer item, Throwable t) {
        log.info("CustomerSkipListener onSkipInProcess");
        log.warn("skipping for raised exception : {} for row : {}", t.getMessage(), item);
    }

    @Override
    protected void doOnSkipInWrite(Customer item, Throwable t) {
        log.info("CustomerSkipListener onSkipInWrite");
        log.warn("skipping for raised exception : {} for row : {}", t.getMessage(), item);
    }
}
