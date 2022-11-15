package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.TotalCostResponse;
import com.myfinancemap.app.dto.transaction.CreateUpdateTransactionDto;
import com.myfinancemap.app.dto.transaction.DetailedTransactionDto;
import com.myfinancemap.app.dto.transaction.TransactionDto;
import com.myfinancemap.app.mapper.TransactionMapper;
import com.myfinancemap.app.persistence.domain.Transaction;
import com.myfinancemap.app.persistence.repository.TransactionRepository;
import com.myfinancemap.app.service.interfaces.ShopService;
import com.myfinancemap.app.service.interfaces.TransactionService;
import com.myfinancemap.app.service.interfaces.UserService;
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

    private final UserService userService;
    private final ShopService shopService;

    public DefaultTransactionService(TransactionRepository transactionRepository,
                                     TransactionMapper transactionMapper,
                                     UserService userService, ShopService shopService) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.userService = userService;
        this.shopService = shopService;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<TransactionDto> getTransactionListById(final Long userId) {
        return transactionMapper.toTransactionDtoList(
                transactionRepository.findByUserId(userId));
    }

    @Override
    public DetailedTransactionDto getTransactionById(final Long transactionId) {
        return transactionMapper.toDetailedTransactionDto(transactionRepository.getTransactionByTransactionId(transactionId).orElseThrow(() -> {
            throw new NoSuchElementException("Tranzakció nem található!");
        }));
    }

    /**
     * @inheritDoc
     */
    @Override
    public TransactionDto createTransaction(final Long userId,
                                            final CreateUpdateTransactionDto transactionDto) {
        final Transaction transaction = transactionMapper.toTransaction(transactionDto);
        transaction.setUser(userService.getUserEntityById(userId));
        if (transactionDto.getShopId() != null) {
            transaction.setShop(shopService.getShopEntityById(transactionDto.getShopId()));
        }
        transactionRepository.save(transaction);
        return transactionMapper.toTransactionDto(transaction);
    }

    /**
     * @inheritDoc
     */
    @Override
    public TransactionDto updateTransaction(final CreateUpdateTransactionDto transactionDto) {
        final Transaction transaction = transactionRepository.getTransactionByTransactionId(
                        transactionDto.getTransactionId())
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Tranzakció nem található!");
                });
        transaction.setShop(shopService.getShopEntityById(transactionDto.getShopId()));
        transactionMapper.modifyTransaction(transactionDto, transaction);
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
                transactionRepository.findByUserIdAndCurrency(userId, currency));
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<TransactionDto> getIncomeOrOutcome(Long userId, Boolean isIncome) {
        return transactionMapper.toTransactionDtoList(
                transactionRepository.findByUserIdAndIncomeType(userId, isIncome));
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
