package com.med.batch.job.customer.processor;

import com.med.batch.core.processor.AbstractItemProcessor;
import com.med.batch.model.Customer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

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

    @Override
    protected void beforeProcess(Customer item) throws Exception {
        if (Objects.equals(item.getId(), "f0a7bc3e-6c20-4161-9504-773fb5de73d5")) {
           throw new Exception("before process customer id equals 57ae9a4d.....");
        }
    }
}
