package com.myfinancemap.app.persistence.repository;

import com.myfinancemap.app.persistence.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserUserId(final Long userId);

    Optional<Transaction> getTransactionByTransactionId(final Long transactionId);
}
