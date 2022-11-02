package com.myfinancemap.app.mapper;

import com.myfinancemap.app.dto.transaction.CreateUpdateTransactionDto;
import com.myfinancemap.app.dto.transaction.TransactionDto;
import com.myfinancemap.app.persistence.domain.Transaction;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(uses = UserMapper.class)
@Component
public interface TransactionMapper {

    @Named("transactionDtoMapper")
    TransactionDto toTransactionDto(Transaction transaction);

    @IterableMapping(qualifiedByName = "transactionDtoMapper")
    List<TransactionDto> toTransactionDtoList(List<Transaction> transactions);

    Transaction toTransaction(CreateUpdateTransactionDto transactionDto);

    void modifyTransaction(CreateUpdateTransactionDto transactionDto, @MappingTarget Transaction transaction);

}
