package com.myfinancemap.app.mapper;

import com.myfinancemap.app.dto.TransactionDto;
import com.myfinancemap.app.persistence.domain.Transaction;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TransactionMapper {

    @Named("transactionDtoMapper")
    TransactionDto transactionToTransactionDto(TransactionDto transactionDto);

    @IterableMapping(qualifiedByName = "transactionDtoMapper")
    List<TransactionDto> transactionsToTransactionDtoList(List<Transaction> transactions);
}
