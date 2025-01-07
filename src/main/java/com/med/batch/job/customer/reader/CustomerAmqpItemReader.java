package com.med.batch.job.customer.reader;

import com.med.batch.core.reader.AbstractAmqpItemReader;
import com.med.batch.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerAmqpItemReader extends AbstractAmqpItemReader<Customer> {
    @Override
    protected Class<Customer> getItemTypeClass() {
        return Customer.class;
    }
}
