package com.med.batch.interfaces;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.core.io.Resource;

public interface ReaderRepository<T> {
    ItemReader<T> createReader(String name,
                               Resource resource,
                               int lineToSkip,
                               LineMapper<T> lineMapper);
}
