package org.maybak.demo.config;

import lombok.AllArgsConstructor;
import org.maybak.demo.dto.TransactionDto;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
public class CustomTransactionFieldSetMapper implements FieldSetMapper<TransactionDto> {
    @Override
    public TransactionDto mapFieldSet(FieldSet fieldSet) {
        TransactionDto dto = new TransactionDto();
        dto.setAccountNumber(fieldSet.readString("ACCOUNT_NUMBER"));
        dto.setTrxAmount(fieldSet.readBigDecimal("TRX_AMOUNT"));
        dto.setDescription(fieldSet.readString("DESCRIPTION"));
        dto.setTrxDate(LocalDate.parse(fieldSet.readString("TRX_DATE")));
        dto.setTrxTime(LocalTime.parse(fieldSet.readString("TRX_TIME")));
        dto.setCustomerId(fieldSet.readLong("CUSTOMER_ID"));
        return dto;
    }
}
