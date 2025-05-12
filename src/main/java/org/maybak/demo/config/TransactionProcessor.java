package org.maybak.demo.config;

import org.maybak.demo.entity.TransactionEntity;
import org.springframework.batch.item.ItemProcessor;

public class TransactionProcessor implements ItemProcessor<TransactionEntity, TransactionEntity> {

    @Override
    public TransactionEntity process(TransactionEntity transaction) throws Exception {
        return transaction;
    }
}