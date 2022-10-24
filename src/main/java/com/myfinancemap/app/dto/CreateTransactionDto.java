package com.myfinancemap.app.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateTransactionDto extends TransactionDto {
    private MinimalUserDto user;
}
