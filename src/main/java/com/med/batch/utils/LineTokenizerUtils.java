package com.med.batch.utils;

import com.med.batch.enumeration.Deliminator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;

public final class LineTokenizerUtils {

    public static LineTokenizer lineTokenizer(boolean strict, Deliminator delimiter, String... names) {
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(names);
        delimitedLineTokenizer.setStrict(strict);
        delimitedLineTokenizer.setDelimiter(delimiter.getDelimiter());
        return delimitedLineTokenizer;
    }
}
