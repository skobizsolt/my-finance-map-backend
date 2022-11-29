package com.myfinancemap.app.dto.transaction;

import com.myfinancemap.app.dto.shop.ShopDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DetailedTransactionDto extends TransactionDto{
    private ShopDto shop;
}
