package com.myfinancemap.app.dto;

import com.myfinancemap.app.persistence.domain.PaymentMethod;
import com.myfinancemap.app.persistence.domain.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDto {
    private Long transactionId;
    private String currency;
    private BigDecimal cost;
    private PaymentMethod paymentMethod;
    private Boolean isIncome;
    private User user;
}
