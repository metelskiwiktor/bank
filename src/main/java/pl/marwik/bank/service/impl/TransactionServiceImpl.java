package pl.marwik.bank.service.impl;

import com.antkorwin.xsync.XSync;
import org.springframework.stereotype.Service;
import pl.marwik.bank.exception.BankException;
import pl.marwik.bank.exception.ExceptionCode;
import pl.marwik.bank.mapper.TransactionMapper;
import pl.marwik.bank.model.AccountStatus;
import pl.marwik.bank.model.OperationType;
import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.entity.Transaction;
import pl.marwik.bank.model.entity.TransferLimit;
import pl.marwik.bank.model.helper.TransferMoney;
import pl.marwik.bank.model.request.TransactionTransferDTO;
import pl.marwik.bank.model.request.TransactionTransferSelfDTO;
import pl.marwik.bank.repository.AccountRepository;
import pl.marwik.bank.repository.TransactionRepository;
import pl.marwik.bank.service.OAuthService;
import pl.marwik.bank.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private OAuthService oAuthService;
    private XSync<String> xSync;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, OAuthService oAuthService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.oAuthService = oAuthService;
        this.xSync = new XSync<>();
    }

    @Override
    public void transfer(String tokenValue, TransactionTransferDTO transactionTransferDTO) throws BankException {
        xSync.execute(transactionTransferDTO.getSenderAccountNumber(),
                () -> xSync.execute(transactionTransferDTO.getRecipientAccountNumber(), () -> {
                    transactionTransferDTO.setOperationType(OperationType.TRANSFER);

                    Account from = getAccountByAccountNumber(transactionTransferDTO.getSenderAccountNumber());
                    Account to = getAccountByAccountNumber(transactionTransferDTO.getRecipientAccountNumber());

                    oAuthService.authorize(tokenValue, from);

                    throwIfAccountIsNotValid(from);
                    throwIfAccountIsNotValid(to);
                    throwIfTakenLimit(transactionTransferDTO.getAmount(), from.getAccountNumber(), OperationType.TRANSFER);
                    throwIfAmountIsSmallerThanMinimum(transactionTransferDTO.getAmount());
                    throwIfAmountIsSmallerThanBalance(from.getBalance(), transactionTransferDTO.getAmount());

                    if(transactionTransferDTO.isCheckBalance()){
                        throwIfBalanceIsDifference(from.getBalance(), transactionTransferDTO.getSenderBalance());
                    }

                    createTransaction(transactionTransferDTO, from, to, LocalDateTime.now());

                    transfer(from, to, transactionTransferDTO.getAmount());
                }));
    }

    @Override
    public void deposit(String tokenValue, TransactionTransferSelfDTO transactionTransferSelfDTO) throws BankException {
        xSync.execute(transactionTransferSelfDTO.getSenderAccountNumber(), () -> {
            transactionTransferSelfDTO.setOperationType(OperationType.DEPOSIT);

            Account account = getAccountByAccountNumber(transactionTransferSelfDTO.getSenderAccountNumber());

            oAuthService.authorize(tokenValue, account);

            throwIfAccountIsNotValid(account);
            throwIfTakenLimit(transactionTransferSelfDTO.getAmount(), account.getAccountNumber(), OperationType.DEPOSIT);
            throwIfAmountIsSmallerThanMinimum(transactionTransferSelfDTO.getAmount());
            throwIfBalanceIsDifference(account.getBalance(), transactionTransferSelfDTO.getSenderBalance());

            createTransaction(transactionTransferSelfDTO, account, LocalDateTime.now());

            transferSelf(account, transactionTransferSelfDTO.getAmount(), TransferMoney.RECIPIENT);
        });
    }

    @Override
    public void withdraw(String tokenValue, TransactionTransferSelfDTO transactionTransferSelfDTO) throws BankException {
        xSync.execute(transactionTransferSelfDTO.getSenderAccountNumber(), () -> {
            transactionTransferSelfDTO.setOperationType(OperationType.WITHDRAW);

            Account account = getAccountByAccountNumber(transactionTransferSelfDTO.getSenderAccountNumber());

            oAuthService.authorize(tokenValue, account);

            throwIfAccountIsNotValid(account);
            throwIfTakenLimit(transactionTransferSelfDTO.getAmount(), account.getAccountNumber(), OperationType.WITHDRAW);
            throwIfAmountIsSmallerThanMinimum(transactionTransferSelfDTO.getAmount());
            throwIfAmountIsSmallerThanBalance(account.getBalance(), transactionTransferSelfDTO.getAmount());
            throwIfBalanceIsDifference(account.getBalance(), transactionTransferSelfDTO.getSenderBalance());

            createTransaction(transactionTransferSelfDTO, account, LocalDateTime.now());

            transferSelf(account, transactionTransferSelfDTO.getAmount(), TransferMoney.SENDER);
        });
    }

    private void createTransaction(TransactionTransferSelfDTO transactionTransferSelfDTO, Account account, LocalDateTime localDateTime) {
        Transaction transaction = TransactionMapper.map(transactionTransferSelfDTO, account, localDateTime);

        transactionSave(transaction);
    }

    private void createTransaction(TransactionTransferDTO transactionTransferDTO, Account from, Account to, LocalDateTime transactionDate) {
        Transaction transaction = TransactionMapper.map(transactionTransferDTO, from, to, transactionDate);

        transactionSave(transaction);
    }

    private void transactionSave(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    private void transferSelf(Account account, BigDecimal amount, TransferMoney transferMoney) {
        setBalance(account, amount, transferMoney);

        accountRepository.save(account);
    }

    private void transfer(Account from, Account to, BigDecimal amount) {
        setBalance(from, amount, TransferMoney.SENDER);
        setBalance(to, amount, TransferMoney.RECIPIENT);

        accountRepository.save(from);
        accountRepository.save(to);
    }

    private void setBalance(Account account, BigDecimal amount, TransferMoney transferMoney) {
        account.setBalance(transferMoney.transfer(account.getBalance(), amount));
    }

    private void throwIfAmountIsSmallerThanMinimum(BigDecimal amount) {
        BigDecimal minimum = BigDecimal.ONE;
        if (amount.compareTo(minimum) < 0) {
            throw new BankException((ExceptionCode.AMOUNT_IS_TOO_SMALL));
        }
    }

    private void throwIfAmountIsSmallerThanBalance(BigDecimal amount, BigDecimal balance) {
        if (amount.compareTo(balance) < 0) {
            throw new BankException((ExceptionCode.BALANCE_IS_TOO_SMALL));
        }
    }

    private void throwIfBalanceIsDifference(BigDecimal balanceGot, BigDecimal balanceNow) {
        if (balanceGot.compareTo(balanceNow) != 0) {
            throw new BankException((ExceptionCode.BALANCE_IS_DIFFERENCE));
        }
    }

    private Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository
                .findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankException(ExceptionCode.ACCOUNT_NOT_FOUND));
    }

    private LocalDateTime getAfter(){
        LocalDate today = LocalDate.now();
        return today.atStartOfDay();
    }

    private LocalDateTime getBefore(){
        LocalDate today = LocalDate.now().plusDays(1L);
        return today.atStartOfDay();
    }

    private void throwIfTakenLimit(BigDecimal amount, String accountNumber, OperationType operationType){
        BigDecimal sum = transactionRepository.findAllByActionTimeAfterAndActionTimeBeforeAndFrom_AccountNumberAndOperationType(getAfter(), getBefore(), accountNumber, operationType)
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(amount);

        // TODO: 01.03.2020 delete this sout
        System.out.println(sum + "," + operationType);

        TransferLimit transferLimit = accountRepository.findAccountByAccountNumber(accountNumber).orElseThrow(() -> new BankException(ExceptionCode.ACCOUNT_NOT_FOUND)).getTransferLimit();

        if(getLimit(operationType, transferLimit).compareTo(sum) < 0){
            throw new BankException(ExceptionCode.LIMIT_REACHED);
        }
    }

    private BigDecimal getLimit(OperationType operationType, TransferLimit transferLimit){
        BigDecimal limit = BigDecimal.ZERO;
        if(operationType.equals(OperationType.TRANSFER)){
            limit = transferLimit.getTransferLimit();
        }
        if(operationType.equals(OperationType.DEPOSIT)){
            limit = transferLimit.getDepositLimit();
        }
        if(operationType.equals(OperationType.WITHDRAW)){
            limit = transferLimit.getWithdrawLimit();
        }

        return limit;
    }

    private void throwIfAccountIsNotValid(Account account){
        if(!account.getAccountStatus().equals(AccountStatus.VALID)){
            throw new BankException(ExceptionCode.ACCOUNT_IS_BLOCKED);
        }
    }
}
