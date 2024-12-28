package com.med.batch.job.customer.reader;

import com.med.batch.core.reader.AbstractFileItemReader;
import com.med.batch.interfaces.Deliminator;
import com.med.batch.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerReader extends AbstractFileItemReader<Customer> {
    @Override
    protected String[] getColumnNames() {
        return new String[]{"id", "firstName", "lastName", "salary"};
    }

    @Override
    protected Class<Customer> getTargetClass() {
        return Customer.class;
    }

    @Override
    protected Deliminator getDelimiter() {
        return Deliminator.COMMA;
    }

    @Override
    protected int getLinesToSkip() {
        return 1;
    }
}
