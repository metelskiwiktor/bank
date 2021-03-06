package pl.marwik.bank.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.marwik.bank.model.OperationType;
import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    Page<Transaction> findAllByAmountGreaterThanAndFrom_AccountNumberAndLocalDateTimeBetween(BigDecimal amount, String accountNumber, LocalDateTime from, LocalDateTime to, Pageable pageable)
    List<Transaction> findAllByFrom_AccountNumberOrTo_AccountNumber(String accountNumberFrom, String accountNumberTo);
    List<Transaction> findAllByActionTimeAfterAndActionTimeBeforeAndFrom_AccountNumberAndOperationType(LocalDateTime after, LocalDateTime before, String from_accountNumber, OperationType operationType);
}
