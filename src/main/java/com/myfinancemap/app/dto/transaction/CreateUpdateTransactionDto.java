package com.myfinancemap.app.dto.transaction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CreateUpdateTransactionDto extends TransactionDto {
    private Long shopId;
}
