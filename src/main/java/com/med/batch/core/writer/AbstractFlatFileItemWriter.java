package com.med.batch.core.writer;

import com.med.batch.core.writer.interfaces.IItemStreamItemWriter;
import com.med.batch.interfaces.Deliminator;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.core.io.WritableResource;

@Slf4j
public abstract class AbstractFlatFileItemWriter<T> implements IItemStreamItemWriter<T> {

    private FlatFileItemWriter<T> flatFileItemWriter;

    protected abstract String[] getColumnNames();

    protected abstract Class<T> getTargetClass();

    protected abstract Deliminator getDelimiter();

    protected abstract WritableResource getResource();

    @PostConstruct
    private void initializeWriter() {
        log.info("Initializing FlatFileItemWriter for target class {}", getTargetClass().getSimpleName());
        this.flatFileItemWriter = new FlatFileItemWriterBuilder<T>()
                .name("FLAT-FILE-WRITER-" + getTargetClass().getSimpleName().toUpperCase())
                .resource(getResource())
                .delimited()
                .delimiter(getDelimiter().getDelimiter())
                .names(getColumnNames())
                .build();
        try {
            this.flatFileItemWriter.afterPropertiesSet();
        } catch (Exception e) {
            log.error("Error during initialization of FlatFileItemWriter", e);
            throw new RuntimeException("Failed to initialize FlatFileItemWriter", e);
        }
    }


    @Override
    public void write(Chunk<? extends T> chunk) throws Exception {
        log.info("Writing {} items to flat file", chunk.getItems().size());
        flatFileItemWriter.write(chunk);
    }

    @Override
    public void open(@NonNull ExecutionContext executionContext) {
        if (flatFileItemWriter != null) {
            flatFileItemWriter.open(executionContext);
        }
    }

    @Override
    public void close() {
        if (flatFileItemWriter != null) {
            flatFileItemWriter.close();
        }
    }
}
