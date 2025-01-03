package com.med.batch.job.customer.processor;

import com.med.batch.core.processor.AbstractItemProcessor;
import com.med.batch.exception.LineNotFormated;
import com.med.batch.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CustomerProcessor extends AbstractItemProcessor<Customer, Customer> {
    @Override
    protected boolean validate(Customer customer) throws LineNotFormated {
        boolean isValid = customer.isValid();
        if (!isValid) {
            log.error("Customer is not valid {}", customer);
            throw new LineNotFormated("Customer is not valid");
        }
        return true;
    }

    @Override
    protected Customer doProcess(Customer customer) {
        customer.setLastName(customer.getLastName().toUpperCase());
        customer.setFirstName(customer.getFirstName().toUpperCase());
        return customer;
    }

    @Override
    protected void beforeProcess(Customer item) {
    }
}
