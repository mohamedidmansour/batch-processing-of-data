package com.med.batch.core.reader;

import com.med.batch.interfaces.Deliminator;
import lombok.NonNull;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.core.io.Resource;

public abstract class AbstractFileItemReader<T> implements IBatchItemReader<T> {

    protected abstract String[] getColumnNames();

    protected abstract Class<T> getTargetClass();

    protected abstract Deliminator getDelimiter();

    protected abstract int getLinesToSkip();

    @Override
    public ItemReader<T> createReader(Resource resource) {
        return new FlatFileItemReaderBuilder<T>()
                .name(getTargetClass().getSimpleName().toUpperCase() + "-READER")
                .resource(resource)
                .strict(false)
                .recordSeparatorPolicy(new DefaultRecordSeparatorPolicy() {
                    @Override
                    public boolean isEndOfRecord(@NonNull String line) {
                        return super.isEndOfRecord(line) && !line.trim().isEmpty();
                    }
                })
                .linesToSkip(getLinesToSkip())
                .lineMapper(lineMapper())
                .build();
    }

    private LineMapper<T> lineMapper() {
        DefaultLineMapper<T> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(createLineTokenizer());
        lineMapper.setFieldSetMapper(createFieldSetMapper());
        return lineMapper;
    }

    private FieldSetMapper<T> createFieldSetMapper() {
        BeanWrapperFieldSetMapper<T> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(getTargetClass());
        return fieldSetMapper;
    }

    private LineTokenizer createLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(getDelimiter().getDelimiter());
        lineTokenizer.setNames(getColumnNames());
        lineTokenizer.setStrict(false);
        return lineTokenizer;
    }
}
