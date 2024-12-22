package com.med.batch.job.processor;

import com.med.batch.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Slf4j
@Configuration
public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(@NonNull Customer customer) {
        return customer;
    }
}
