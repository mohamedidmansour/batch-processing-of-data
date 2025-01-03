package com.med.batch.job.customer.processor;

import com.med.batch.core.processor.AbstractItemProcessor;
import com.med.batch.exception.LineNotFormated;
import com.med.batch.model.Customer;
import com.med.batch.model.CustomerDataBNK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CustomerDataBNKProcessor extends AbstractItemProcessor<CustomerDataBNK, Customer> {
    @Override
    protected boolean validate(CustomerDataBNK customer) throws LineNotFormated {
        boolean isValid = customer.isValid();
        if (!isValid) {
            log.error("Customer is not valid {}", customer);
            throw new LineNotFormated("Customer is not valid");
        }
        return true;
    }

    @Override
    protected Customer doProcess(CustomerDataBNK customer) {
        customer.setLastName(customer.getLastName().toLowerCase());
        customer.setFirstName(customer.getFirstName().toLowerCase());
        customer.setSalary("-00000.1");
        return customer.mapperToCustomer();
    }

    @Override
    protected void beforeProcess(CustomerDataBNK item) {
    }
}
