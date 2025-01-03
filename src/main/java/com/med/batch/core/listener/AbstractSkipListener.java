package com.med.batch.core.listener;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;

@Slf4j
public abstract class AbstractSkipListener<T, S> implements SkipListener<T, S> {
    protected abstract void doOnSkipInRead(Throwable t);

    protected abstract void doOnSkipInProcess(T item, Throwable t);

    protected abstract void doOnSkipInWrite(S item, Throwable t);

    @Override
    public void onSkipInProcess(@NonNull T item, @NonNull Throwable t) {
        log.info("onSkipInProcess called");
        doOnSkipInProcess(item, t);
    }

    @Override
    public void onSkipInRead(@NonNull Throwable t) {
        log.info("onSkipInRead called");
        doOnSkipInRead(t);
    }

    @Override
    public void onSkipInWrite(@NonNull S item, @NonNull Throwable t) {
        log.info("onSkipInWrite called");
        doOnSkipInWrite(item, t);
    }
}
