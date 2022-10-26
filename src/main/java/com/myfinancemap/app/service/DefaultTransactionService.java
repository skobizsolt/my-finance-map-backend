package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.TotalCostResponse;
import com.myfinancemap.app.dto.TransactionDto;
import com.myfinancemap.app.mapper.TransactionMapper;
import com.myfinancemap.app.persistence.domain.Transaction;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.repository.TransactionRepository;
import com.myfinancemap.app.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Default implementation of Transaction service.
 */
@Service
public class DefaultTransactionService implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    private final UserRepository userRepository;

    public DefaultTransactionService(TransactionRepository transactionRepository,
                                     TransactionMapper transactionMapper,
                                     UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.userRepository = userRepository;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<TransactionDto> getTransactionListById(final Long userId) {
        return transactionMapper.toTransactionDtoList(
                transactionRepository.findByUserUserId(userId));
    }

    /**
     * @inheritDoc
     */
    @Override
    public TransactionDto createTransaction(final Long userId,
                                            final TransactionDto transactionDto) {
        final Transaction transaction = transactionMapper.toTransaction(transactionDto);
        transaction.setUser(userRepository.getUserByUserId(userId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Felhasználó nem található!");
                }));
        transactionRepository.save(transaction);
        return transactionMapper.toTransactionDto(transaction);
    }

    /**
     * @inheritDoc
     */
    @Override
    public TransactionDto updateTransaction(final TransactionDto transactionDto) {
        final Transaction transaction = transactionRepository.getTransactionByTransactionId(
                        transactionDto.getTransactionId())
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Tranzakció nem található!");
                });
        final User user = transaction.getUser();
        transactionMapper.modifyTransaction(transactionDto, transaction);
        transaction.setUser(user);
        transactionRepository.saveAndFlush(transaction);
        return transactionMapper.toTransactionDto(transaction);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteTransaction(final Long transactionId) {
        final Transaction transaction = transactionRepository.getTransactionByTransactionId(transactionId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Tranzakció nem található!");
                });
        transactionRepository.delete(transaction);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<TransactionDto> getTransactionListByIdAndCurrency(Long userId, String currency) {
        return transactionMapper.toTransactionDtoList(
                transactionRepository.findByUserUserIdAndCurrencyEquals(userId, currency));
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<TransactionDto> getIncomeOrOutcome(Long userId, Boolean isIncome) {
        return transactionMapper.toTransactionDtoList(
                transactionRepository.findByUserUserIdAndIsIncomeEquals(userId, isIncome));
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<TransactionDto> getTransactionsByInterval(Long userId, LocalDate fromDate, LocalDate toDate) {
        if (fromDate.compareTo(toDate) > 0) {
            throw new ValidationException("Kezdő dátum nem előzheti meg a végdátumot!");
        }
        return transactionMapper.toTransactionDtoList(
                transactionRepository.findByInterval(fromDate, toDate, userId));
    }

    /**
     * @inheritDoc
     */
    @Override
    public TotalCostResponse getTotal(final Long userId, final Boolean isIncome) {
        final TotalCostResponse response = new TotalCostResponse();
        response.setCost(transactionRepository.getTotalCost(userId, isIncome));
        return response;
    }
}
