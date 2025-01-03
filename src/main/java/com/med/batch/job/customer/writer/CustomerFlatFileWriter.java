package com.med.batch.job.customer.writer;

import com.med.batch.core.writer.AbstractFlatFileItemWriter;
import com.med.batch.interfaces.Deliminator;
import com.med.batch.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CustomerFlatFileWriter extends AbstractFlatFileItemWriter<Customer> {

    private final String fileOutputPath;

    public CustomerFlatFileWriter(@Value("${batch.output.s1}") String fileOutputPath) {
        this.fileOutputPath = fileOutputPath;
    }

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
        return Deliminator.PIPE;
    }

    @Override
    protected WritableResource getResource() {
        return new FileSystemResource(fileOutputPath);
    }
}

