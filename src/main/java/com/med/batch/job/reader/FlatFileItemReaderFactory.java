package com.med.batch.job.reader;

import com.med.batch.interfaces.ReaderRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class FlatFileItemReaderFactory<R> implements ReaderRepository<R> {
    @Override
    public ItemReader<R> createReader(String name,
                                      Resource resource,
                                      @DefaultValue(value = "1")
                                      int lineToSkip,
                                      LineMapper<R> lineMapper) {
        return new FlatFileItemReaderBuilder<R>()
                .name(name)
                .resource(resource)
                .linesToSkip(lineToSkip)
                .strict(false)
                .lineMapper(lineMapper)
                .build();
    }
}
