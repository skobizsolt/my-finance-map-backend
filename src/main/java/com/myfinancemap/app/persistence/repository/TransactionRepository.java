package com.myfinancemap.app.persistence.repository;

import com.myfinancemap.app.persistence.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserUserId(final Long userId);

    List<Transaction> findByUserUserIdAndCurrencyEquals(final Long userId, final String currency);

    List<Transaction> findByUserUserIdAndIsIncomeEquals(final Long userId, final Boolean isIncome);

    @Query(value = "SELECT * FROM transaction t" +
            "WHERE t.user_id = :userId" +
            "AND t.issued_at BETWEEN :fromDate AND :toDate" +
            "ORDER BY t.issued_at DESC", nativeQuery = true)
    List<Transaction> findByInterval(final LocalDate fromDate,
                                     final LocalDate toDate,
                                     final Long userId);

    Optional<Transaction> getTransactionByTransactionId(final Long transactionId);

    @Query(value = "SELECT sum(t.cost) FROM transaction t" +
            "       WHERE t.user_id = :userId " +
            "       AND t.is_income = :isIncome", nativeQuery = true)
    BigDecimal getTotalCost(final Long userId, final Boolean isIncome);

}
