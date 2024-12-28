package com.med.batch.job.customer.writer;

import com.med.batch.core.writer.AbstractItemWriter;
import com.med.batch.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CustomerWriter extends AbstractItemWriter<Customer> {

    @Override
    protected void doWrite(Chunk<? extends Customer> items) {
        log.info("Customers found: {}", items.size());
        log.info("Writing customer data : {}", items);
    }
}
