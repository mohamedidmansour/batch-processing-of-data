package com.med.batch.core.writer;

import lombok.NonNull;
import org.springframework.batch.item.Chunk;

public abstract class AbstractItemWriter<T> implements IBatchItemWriter<T> {

    @Override
    public void write(@NonNull Chunk<? extends T> chunk) throws Exception {
        beforeWrite(chunk);
        doWrite(chunk);
        afterWrite(chunk);
    }

    protected void beforeWrite(Chunk<? extends T> items) {
    }

    protected abstract void doWrite(Chunk<? extends T> items) throws Exception;

    protected void afterWrite(Chunk<? extends T> items) {
    }
}
