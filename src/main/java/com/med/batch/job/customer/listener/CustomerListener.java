package com.med.batch.job.customer.listener;

import com.med.batch.model.Customer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;

@Slf4j
public class CustomerListener implements SkipListener<Customer, Customer> {

    @Override
    public void onSkipInProcess(@NonNull Customer item, @NonNull Throwable t) {
        tracingLog("CustomerSkipListener onSkipInProcess called", item, t);
    }

    @Override
    public void onSkipInRead(@NonNull Throwable t) {
        tracingLog("CustomerSkipListener onSkipInRead called", null, t);
    }

    @Override
    public void onSkipInWrite(@NonNull Customer item, Throwable t) {
        tracingLog("CustomerSkipListener onSkipInWrite called", item, t);
    }

    private static void tracingLog(String CustomerSkipListener_onSkipInWrite_called, Customer item, Throwable t) {
        log.info(CustomerSkipListener_onSkipInWrite_called);
        log.info("item = {} skipped for raison [{}]", item, t.getMessage());
    }
}
