package pl.marwik.bank.service.impl;

import com.antkorwin.xsync.XSync;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.marwik.bank.exception.BankException;
import pl.marwik.bank.exception.ExceptionCode;
import pl.marwik.bank.initializer.AccountInitialize;
import pl.marwik.bank.mapper.UserMapper;
import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.entity.Branch;
import pl.marwik.bank.model.entity.Transaction;
import pl.marwik.bank.model.entity.User;
import pl.marwik.bank.model.request.CreateAccountDTO;
import pl.marwik.bank.model.request.UserDTO;
import pl.marwik.bank.repository.AccountRepository;
import pl.marwik.bank.repository.BranchRepository;
import pl.marwik.bank.repository.TransactionRepository;
import pl.marwik.bank.repository.UserRepository;
import pl.marwik.bank.service.AccountService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private BranchRepository branchRepository;
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private XSync<String> xSync;

    public AccountServiceImpl(AccountRepository accountRepository, BranchRepository branchRepository, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.branchRepository = branchRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.xSync = new XSync<>();
    }

    @Override
    public Page<Transaction> getHistory(String accountNumber, LocalDate dateFrom, LocalDate dateTo, BigDecimal amount) throws BankException {
        Pageable pageable = Pageable.unpaged();

        return transactionRepository.findAllByFrom_AccountNumber(accountNumber, pageable);
    }

    @Override
    public void addUser(UserDTO userDTO) {
        System.out.println("Początek metody");
        xSync.execute(userDTO.getIdCard(), () -> {
            Account account = getAccountByAccountNumber(userDTO.getAccountNumber());

            User user = UserMapper.map(userDTO);

            addUser(user, account);
        });
    }

    @Override
    public void createAccount(CreateAccountDTO createAccountDTO) {
        System.out.println(createAccountDTO);
        Branch branch = getBranchByBranchName(createAccountDTO.getBranchName());

        Account account = AccountInitialize.generate();
        User user = UserMapper.map(createAccountDTO.getUserDTO());
        throwIfUserExist(user.getIDCard());

        accountRepository.save(account);
        addUser(user, account);

        branch.addAccount(account);

        branchRepository.save(branch);
    }

    private void addUser(User user, Account account) {
        throwIfUserExist(user.getIDCard());

        account.addUser(user);

        userRepository.save(user);
        accountRepository.save(account);
    }

    private void throwIfUserExist(String IDCard) {
        Optional<User> user = userRepository.getUserByIDCard(IDCard);

        if(user.isPresent()){
            throw new BankException(ExceptionCode.USER_ALREADY_EXIST);
        }
    }

    private Branch getBranchByBranchName(String branchName) {
        return branchRepository
                .findBranchByBranchName(branchName)
                .orElseThrow(() -> new BankException(ExceptionCode.BRANCH_NOT_FOUND));
    }

    private Account getAccountByAccountNumber(String accountNumber) throws BankException {
        return accountRepository
                .findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankException(ExceptionCode.ACCOUNT_NOT_FOUND));
    }
}
