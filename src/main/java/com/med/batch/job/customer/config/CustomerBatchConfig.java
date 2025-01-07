package com.med.batch.job.customer.config;

import com.med.batch.core.processor.IBatchItemProcessor;
import com.med.batch.core.reader.IBatchItemReader;
import com.med.batch.core.reader.IBatchItemReaderMq;
import com.med.batch.core.writer.interfaces.IItemStreamItemWriter;
import com.med.batch.exception.LineNotFormated;
import com.med.batch.model.Customer;
import com.med.batch.model.CustomerDataBNK;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;

@RequiredArgsConstructor
@Configuration
public class CustomerBatchConfig {
    private static final String CUSTOMER_STEP_1_NAME = "customerStep1";
    private static final String CUSTOMER_STEP_2_NAME = "customerStep2";
    private static final String CUSTOMER_STEP_3_NAME = "customerStep3";

    private static final String CUSTOMER_STEP_MULTI_FILES_NAME = "customerMultiFilesStep";
    private static final String CUSTOMER_JOB_1_NAME = "customerJob1";
    private static final String CUSTOMER_JOB_2_NAME = "customerJob2";

    private static final int CHUNK = 200;
    private static final int SKIP_LIMIT = 5;
    private static final int RETRY_LIMIT = 5;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final IBatchItemReader<Customer> customerReader;
    private final IBatchItemReader<CustomerDataBNK> customerDataBNKReader;
    private final IBatchItemReaderMq<Customer> customerAmqpItemReader;

    private final IBatchItemProcessor<Customer, Customer> customerProcessor;
    private final IBatchItemProcessor<CustomerDataBNK, Customer> customerDataBNKProcessor;

    private final IItemStreamItemWriter<Customer> customerFlatFileWriter;

    private final SkipListener<Customer, Customer> customerSkipListener;
    private final StepExecutionListener stepExecutionListener;

    @Value("${batch.resource.s1}")
    private String customerSource1; // AN_data_1_000_000.min.txt
    @Value("${batch.resource.s2}")
    private String customerSource2; // CIH_BNK_data_100_000.min.txt
    @Value("${batch.resource.s3}")
    private String customerSource3; // datasource/muti-files/*.txt

    // step 1 and step 2 (extract data from different datasource)
    @Bean
    public Step customerStep1() {
        return new StepBuilder(CUSTOMER_STEP_1_NAME, jobRepository)
                .<Customer, Customer>chunk(CHUNK, transactionManager)
                .reader(customerReader.createReader(new FileSystemResource(customerSource1)))
                .processor(customerProcessor)
                .writer(customerFlatFileWriter)
                .faultTolerant()
                .skip(LineNotFormated.class)
                .skipLimit(SKIP_LIMIT)
                .listener(customerSkipListener)
                .listener(stepExecutionListener)
                .build();
    }

    @Bean
    public Step customerStep2() {
        return new StepBuilder(CUSTOMER_STEP_2_NAME, jobRepository)
                .<CustomerDataBNK, Customer>chunk(CHUNK, transactionManager)
                .reader(customerDataBNKReader.createReader(new FileSystemResource(customerSource2)))
                .processor(customerDataBNKProcessor)
                .writer(customerFlatFileWriter)
                .faultTolerant()
                .skip(LineNotFormated.class)
                .skipLimit(SKIP_LIMIT)
                .listener(customerSkipListener)
                .listener(stepExecutionListener)
                .build();
    }

    @Bean
    public Step customerMQStep3() {
        return new StepBuilder(CUSTOMER_STEP_3_NAME, jobRepository)
                .<Customer, Customer>chunk(CHUNK, transactionManager)
                .reader(customerAmqpItemReader.createReader())
                //.listener(new RabbitMQItemReadListener<>())
                .processor(customerProcessor)
                .writer(customerFlatFileWriter)
                .build();
    }

    // !! all files must be in some format
    @Bean
    public Step customersStepMultiFiles() throws IOException {
        return new StepBuilder(CUSTOMER_STEP_MULTI_FILES_NAME, jobRepository)
                .<Customer, Customer>chunk(CHUNK, transactionManager)
                .reader(customerReader.createReaderBaseInMultiResource(getResources()))
                .processor(customerProcessor)
                .writer(customerFlatFileWriter)
                .faultTolerant()
                .skip(LineNotFormated.class)
                .skipLimit(SKIP_LIMIT)
                .listener(customerSkipListener)
                .listener(stepExecutionListener)
                .build();
    }

    @Bean
    public Flow splitFlow() throws IOException {
        return new FlowBuilder<Flow>("FLOW-SPLIT-CUSTOMERS")
                .split(taskExecutor())
                .add(
                        new FlowBuilder<Flow>("FLOW-CUSTOMER-STEP1")
                                .start(customerStep1())
                                .build(),
                        new FlowBuilder<Flow>("FLOW-CUSTOMER-STEP2")
                                .start(customerStep2())
                                .build()
                )
                .next(customersStepMultiFiles())
                .build();
    }

    @Bean
    public Job customerJob() throws IOException {
        return new JobBuilder(CUSTOMER_JOB_1_NAME, jobRepository)
                //.listener(new JobExecutionListener())
                .start(customerMQStep3())
                .build(); // to build FowBuilder
        //.build(); // to build job
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // nb of threads MIN
        executor.setCorePoolSize(4);
        // nb of threads MAX
        executor.setMaxPoolSize(8);
        executor.setThreadNamePrefix("MED-BATCH-parallel-");
        return executor;
    }

    private @NonNull Resource[] getResources() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResources(customerSource3);
    }
}

   /*
        => note : be attention to order of listener ex.
        .listener(customerSkipListener)
        .listener(stepExecutionListener) // working
        But
        .listener(stepExecutionListener)
        .listener(customerSkipListener) // not working

        * */