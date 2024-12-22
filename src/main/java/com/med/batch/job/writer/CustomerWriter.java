package com.med.batch.job.writer;

import com.med.batch.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CustomerWriter {

    @Bean
    public ItemWriter<Customer> customerItemWriter() {
        return chunk -> {
            log.info("Writing customer data - nb customer(s) {}", chunk.size());
            log.info(chunk.toString());
        };
    }
}
