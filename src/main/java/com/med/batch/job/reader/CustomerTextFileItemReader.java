package com.med.batch.job.reader;

import com.med.batch.enumeration.Deliminator;
import com.med.batch.interfaces.ReaderRepository;
import com.med.batch.model.Customer;
import com.med.batch.utils.LineTokenizerUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import static com.med.batch.constant.NamesTokenizer.CUSTOMERS_COLUMNS;
import static com.med.batch.utils.LineMapperUtils.lineMapper;

@Slf4j
@Configuration
public class CustomerTextFileItemReader {
    private final static String NAME_CUSTOMER = "READER-CUSTOMER-FILE";

    private final String customerFilePath;
    private final ReaderRepository<Customer> readerFlatFileFactory;

    public CustomerTextFileItemReader(@Value("${batch.resource}") String customerFilePath,
                                      ReaderRepository<Customer> readerFlatFileFactory) {
        this.customerFilePath = customerFilePath;
        this.readerFlatFileFactory = readerFlatFileFactory;
    }

    @Bean
    public ItemReader<Customer> customerItemReader() {
        return readerFlatFileFactory.createReader(NAME_CUSTOMER, new FileSystemResource(customerFilePath), 1, lineMapperCustomer());
    }

    private LineMapper<Customer> lineMapperCustomer() {
        LineTokenizer lineTokenizer = LineTokenizerUtils.lineTokenizer(false, Deliminator.COMMA, CUSTOMERS_COLUMNS);
        BeanWrapperFieldSetMapper<Customer> mapper = new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(Customer.class);
        return lineMapper(mapper, lineTokenizer);
    }
}
