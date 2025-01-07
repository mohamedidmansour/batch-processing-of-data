package com.med.batch.core.reader;

import org.springframework.batch.item.ItemReader;

public interface IBatchItemReaderMq<T> {
    ItemReader<T> createReader();
}
