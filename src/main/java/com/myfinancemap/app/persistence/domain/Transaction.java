package com.myfinancemap.app.persistence.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private String description;
    @NotNull
    @Size(min = 3, max = 3, message = "Exactly 3 characters required")
    private String currency;
    @NotNull
    @Min(0)
    private BigDecimal cost;
    @NotNull
    private PaymentMethod paymentMethod;
    @NotNull
    private Boolean isIncome;
    @NotNull
    @PastOrPresent
    private LocalDate issuedAt;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    @ManyToOne
    @JoinColumn(name = "shopId", referencedColumnName = "shopId")
    private Shop shop;
}
