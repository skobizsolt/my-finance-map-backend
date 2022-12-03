package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.TotalCostResponse;
import com.myfinancemap.app.dto.transaction.CreateUpdateTransactionDto;
import com.myfinancemap.app.dto.transaction.DetailedTransactionDto;
import com.myfinancemap.app.dto.transaction.TransactionDto;
import com.myfinancemap.app.exception.ServiceExpection;
import com.myfinancemap.app.mapper.TransactionMapper;
import com.myfinancemap.app.persistence.domain.Transaction;
import com.myfinancemap.app.persistence.repository.TransactionRepository;
import com.myfinancemap.app.service.interfaces.ShopService;
import com.myfinancemap.app.service.interfaces.TransactionService;
import com.myfinancemap.app.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.myfinancemap.app.exception.Error.INCORRECT_DATE_ORDER;
import static com.myfinancemap.app.exception.Error.TRANSACTION_NOT_FOUND;

/**
 * Default implementation of Transaction service.
 */
@Service
@AllArgsConstructor
public class DefaultTransactionService implements TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;
    @Autowired
    private final TransactionMapper transactionMapper;
    @Autowired
    private final UserService userService;
    @Autowired
    private final ShopService shopService;

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasUser(#userId)")
    public List<TransactionDto> getTransactionListById(final Long userId) {
        return transactionMapper.toTransactionDtoList(
                transactionRepository.findByUserId(userId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasUser(#userId)")
    public DetailedTransactionDto getTransactionById(final Long transactionId) {
        return transactionMapper.toDetailedTransactionDto(getTransactionByItsId(transactionId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasUser(#userId)")
    public DetailedTransactionDto createTransaction(final Long userId,
                                                    final CreateUpdateTransactionDto transactionDto) {
        final Transaction transaction = transactionMapper.toTransaction(transactionDto);
        transaction.setUser(userService.getUserEntityById(userId));
        if (transactionDto.getShopId() != null) {
            transaction.setShop(shopService.getShopEntityById(transactionDto.getShopId()));
        }
        transactionRepository.save(transaction);
        return transactionMapper.toDetailedTransactionDto(transaction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public TransactionDto updateTransaction(final CreateUpdateTransactionDto transactionDto) {
        final Transaction transaction = getTransactionByItsId(transactionDto.getTransactionId());
        transaction.setShop(shopService.getShopEntityById(transactionDto.getShopId()));
        transactionMapper.modifyTransaction(transactionDto, transaction);
        transactionRepository.saveAndFlush(transaction);
        return transactionMapper.toTransactionDto(transaction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasUser(#userId)")
    public void deleteTransaction(final Long transactionId) {
        final Transaction transaction = getTransactionByItsId(transactionId);
        transactionRepository.delete(transaction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasUser(#userId)")
    public List<TransactionDto> getTransactionListByIdAndCurrency(Long userId, String currency) {
        return transactionMapper.toTransactionDtoList(
                transactionRepository.findByUserIdAndCurrency(userId, currency));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasUser(#userId)")
    public List<TransactionDto> getIncomeOrOutcome(Long userId, Boolean isIncome) {
        return transactionMapper.toTransactionDtoList(
                transactionRepository.findByUserIdAndIncomeType(userId, isIncome));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasUser(#userId)")
    public List<TransactionDto> getTransactionsByInterval(Long userId, LocalDate fromDate, LocalDate toDate) {
        if (fromDate.compareTo(toDate) > 0) {
            throw new ServiceExpection(INCORRECT_DATE_ORDER);
        }
        return transactionMapper.toTransactionDtoList(
                transactionRepository.findByInterval(fromDate, toDate, userId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasUser(#userId)")
    public TotalCostResponse getTotal(final Long userId, final Boolean isIncome) {
        final TotalCostResponse response = new TotalCostResponse();
        response.setCost(transactionRepository.getTotalCost(userId, isIncome));
        return response;
    }

    private Transaction getTransactionByItsId(Long transactionId) {
        return transactionRepository.getTransactionByTransactionId(transactionId).orElseThrow(() -> {
            throw new ServiceExpection(TRANSACTION_NOT_FOUND);
        });
    }
}
