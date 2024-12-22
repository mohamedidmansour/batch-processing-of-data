package com.med.batch.utils;

import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;

public final class LineMapperUtils {

    public static <T> LineMapper<T> lineMapper(FieldSetMapper<T> fieldSetMapper, LineTokenizer lineTokenizer) {
        DefaultLineMapper<T> lineMapper = new DefaultLineMapper<>();
        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(lineTokenizer);
        return lineMapper;
    }
}
