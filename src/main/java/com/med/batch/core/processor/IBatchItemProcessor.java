package com.med.batch.core.processor;

import org.springframework.batch.item.ItemProcessor;

public interface IBatchItemProcessor<I, O> extends ItemProcessor<I, O> {
}


