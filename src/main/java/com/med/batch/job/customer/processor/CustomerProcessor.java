package com.med.batch.job.customer.processor;

import com.med.batch.core.processor.AbstractItemProcessor;
import com.med.batch.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerProcessor extends AbstractItemProcessor<Customer, Customer> {
    @Override
    protected boolean validate(Customer customer) {
        return customer != null;
    }

    @Override
    protected Customer doProcess(Customer customer) {
        customer.setLastName(customer.getLastName().toUpperCase());
        customer.setFirstName(customer.getFirstName().toUpperCase());
        return customer;
    }
}
