package com.myfinancemap.app.dto;

import com.myfinancemap.app.persistence.domain.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDto {
    private Long transactionId;
    private String currency;
    private BigDecimal cost;
    private PaymentMethod paymentMethod;
    private Boolean isIncome;
    private LocalDate issuedAt;
}
