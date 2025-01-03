package com.med.batch.job.customer.reader;

import com.med.batch.core.reader.AbstractFileItemReader;
import com.med.batch.interfaces.Deliminator;
import com.med.batch.model.CustomerDataBNK;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataBNKReader extends AbstractFileItemReader<CustomerDataBNK> {
    @Override
    protected String[] getColumnNames() {
        return new String[]{"bankname", "id", "firstName", "lastName", "salary"};
    }

    @Override
    protected Class<CustomerDataBNK> getTargetClass() {
        return CustomerDataBNK.class;
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
