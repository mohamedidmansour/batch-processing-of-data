package com.med.batch.core.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.Resource;

public interface IBatchItemReader<T> {
    ItemReader<T> createReader(Resource resource);
}
