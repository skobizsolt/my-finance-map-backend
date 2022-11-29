package com.myfinancemap.app.dto.transaction;

import com.myfinancemap.app.persistence.domain.PaymentMethod;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
public class TransactionDto {
    private Long transactionId;
    private String description;
    private String currency;
    private BigDecimal cost;
    private PaymentMethod paymentMethod;
    private Boolean isIncome;
    private LocalDate issuedAt;
}
