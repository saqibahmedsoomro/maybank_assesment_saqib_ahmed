package org.maybak.demo.config;

import org.maybak.demo.dto.TransactionDto;
import org.springframework.batch.item.ItemProcessor;

public class TransactionProcessor implements ItemProcessor<TransactionDto, TransactionDto> {

    @Override
    public TransactionDto process(TransactionDto transaction) throws Exception {
        return transaction;
    }
}