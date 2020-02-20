package pl.marwik.bank.service.impl;

import com.antkorwin.xsync.XSync;
import org.springframework.stereotype.Service;
import pl.marwik.bank.exception.BankException;
import pl.marwik.bank.exception.ExceptionCode;
import pl.marwik.bank.mapper.TransactionMapper;
import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.entity.Transaction;
import pl.marwik.bank.model.request.TransactionDTO;
import pl.marwik.bank.model.request.TransactionTransferSelfDTO;
import pl.marwik.bank.model.request.TransactionTransferDTO;
import pl.marwik.bank.model.helper.TransferMoney;
import pl.marwik.bank.repository.AccountRepository;
import pl.marwik.bank.repository.TransactionRepository;
import pl.marwik.bank.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private XSync<String> xSync;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.xSync = new XSync<>();
    }

    @Override
    public void transfer(TransactionTransferDTO transactionTransferDTO) throws BankException{
        xSync.execute(transactionTransferDTO.getSenderAccountNumber(), () -> {
            Account to = getAccountByAccountNumber(transactionTransferDTO.getRecipientAccountNumber());
            Account from = transferSelf(transactionTransferDTO, TransferMoney.SENDER);

            transfer(to, TransferMoney.RECIPIENT, transactionTransferDTO.getAmount());

            Transaction transaction = TransactionMapper.map(transactionTransferDTO, from, to, LocalDateTime.now());

            transactionRepository.save(transaction);
            accountRepository.save(from);
            accountRepository.save(to);
        });
    }

    @Override
    public void deposit(TransactionTransferSelfDTO transactionTransferSelfDTO) throws BankException{
        xSync.execute(transactionTransferSelfDTO.getSenderAccountNumber(),
                () -> transferSelf(transactionTransferSelfDTO, TransferMoney.RECIPIENT));
    }

    @Override
    public void withdraw(TransactionTransferSelfDTO transactionTransferSelfDTO) throws BankException{
        xSync.execute(transactionTransferSelfDTO.getSenderAccountNumber(),
                () -> transferSelf(transactionTransferSelfDTO, TransferMoney.SENDER));
    }

    private Account transferSelf(TransactionDTO transactionDTO, TransferMoney transferMoney) throws BankException{
        Account account = getAccountByAccountNumber(transactionDTO.getSenderAccountNumber());

        checkBalanceAndAmount(transactionDTO.getAmount(), account);

        transfer(account, transferMoney, transactionDTO.getAmount());

        return account;
    }

    private void checkBalanceAndAmount(BigDecimal amount, Account account) throws BankException{
        accountRepository
                .findAccountByAccountNumber(account.getAccountNumber())
                .filter(acc -> acc.getBalance().equals(account.getBalance()))
                .orElseThrow(() -> new BankException(ExceptionCode.BALANCE_IS_DIFFERENCE));

        Optional.of(amount.compareTo(BigDecimal.ONE))
                .filter(integer -> integer > 0)
                .orElseThrow(() -> new BankException(ExceptionCode.AMOUNT_IS_TOO_SMALL));

        Optional.of(amount.compareTo(account.getBalance()))
                .filter(integer -> integer > 0)
                .orElseThrow(() -> new BankException(ExceptionCode.BALANCE_IS_TOO_SMALL));
    }

    private void transfer(Account account, TransferMoney transferMoney, BigDecimal price){
        BigDecimal balance = transferMoney.transfer(account.getBalance(), price);
        account.setBalance(balance);
    }

    private Account getAccountByAccountNumber(String accountNumber){
        return accountRepository
                .findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankException(ExceptionCode.ACCOUNT_NOT_FOUND));
    }
}
