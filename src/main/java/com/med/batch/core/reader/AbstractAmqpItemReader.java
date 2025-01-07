package com.med.batch.core.reader;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.amqp.builder.AmqpItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAmqpItemReader<T> implements IBatchItemReaderMq<T> {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    protected abstract Class<T> getItemTypeClass();

    @Override
    public ItemReader<T> createReader() {
        return new AmqpItemReaderBuilder<T>()
                .amqpTemplate(rabbitTemplate)
                .itemType(getItemTypeClass())
                .build();
    }
}
