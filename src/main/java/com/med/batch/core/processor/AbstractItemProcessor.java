package com.med.batch.core.processor;

import com.med.batch.exception.LineNotFormated;
import lombok.NonNull;

public abstract class AbstractItemProcessor<I, O> implements IBatchItemProcessor<I, O> {

    @Override
    public O process(@NonNull I item) throws Exception {
        beforeProcess(item);
        if (validate(item)) {
            return doProcess(item);
        }
        return null;
    }

    protected abstract boolean validate(I item) throws LineNotFormated;

    protected abstract O doProcess(I item) throws Exception;

    protected abstract void beforeProcess(I item) throws Exception;
}
