package com.med.batch.core.reader;

import lombok.NonNull;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.Resource;

public interface IBatchItemReader<T> {
    ItemReader<T> createReader(@NonNull Resource resource);
    ItemReader<T> createReaderBaseInMultiResource(@NonNull Resource[] resource);
}
