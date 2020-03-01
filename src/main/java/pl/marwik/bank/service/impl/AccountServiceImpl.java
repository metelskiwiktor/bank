package pl.marwik.bank.service.impl;

import com.antkorwin.xsync.XSync;
import org.springframework.stereotype.Service;
import pl.marwik.bank.exception.BankException;
import pl.marwik.bank.exception.ExceptionCode;
import pl.marwik.bank.initializer.AccountInitialize;
import pl.marwik.bank.mapper.DetailsMapper;
import pl.marwik.bank.mapper.TransactionMapper;
import pl.marwik.bank.mapper.UserMapper;
import pl.marwik.bank.model.OperationType;
import pl.marwik.bank.model.entity.*;
import pl.marwik.bank.model.helper.Name;
import pl.marwik.bank.model.request.CreateAccountDTO;
import pl.marwik.bank.model.request.UserDTO;
import pl.marwik.bank.model.response.DetailsDTO;
import pl.marwik.bank.model.response.TransactionDTO;
import pl.marwik.bank.repository.*;
import pl.marwik.bank.service.AccountService;
import pl.marwik.bank.service.OAuthService;
import pl.marwik.bank.service.oauth.EncryptionServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private BranchRepository branchRepository;
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private OAuthService oAuthService;
    private TokenRepository tokenRepository;
    private XSync<String> xSync;
    private EncryptionServiceImpl encryptionService;
    private CreditCardRepository creditCardRepository;
    private IdCardRepository idCardRepository;
    private TransferLimitRepository transferLimitRepository;

    public AccountServiceImpl(AccountRepository accountRepository, BranchRepository branchRepository, TransactionRepository transactionRepository, UserRepository userRepository, OAuthService oAuthService, TokenRepository tokenRepository, EncryptionServiceImpl encryptionService, CreditCardRepository creditCardRepository, IdCardRepository idCardRepository, TransferLimitRepository transferLimitRepository) {
        this.accountRepository = accountRepository;
        this.branchRepository = branchRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.oAuthService = oAuthService;
        this.tokenRepository = tokenRepository;
        this.encryptionService = encryptionService;
        this.creditCardRepository = creditCardRepository;
        this.idCardRepository = idCardRepository;
        this.transferLimitRepository = transferLimitRepository;
        this.xSync = new XSync<>();
    }

    @Override
    public List<TransactionDTO> getHistory(String tokenValue) throws BankException {
        Optional<Token> tokenByValue = tokenRepository.findTokenByValue(tokenValue);
        User user = tokenByValue.orElseThrow(() -> new BankException(ExceptionCode.USER_NOT_FOUND)).getUser();
        Account account = accountRepository
                .findAll()
                .stream()
                .filter(acc -> acc.getUsers().contains(user))
                .findFirst()
                .orElseThrow(() -> new BankException(ExceptionCode.ACCOUNT_NOT_FOUND));
        String iSeeMee = Name.getName(account, getBranchByAccount(account));

        List<Transaction> transactions = transactionRepository.findAllByFrom_AccountNumberOrTo_AccountNumber(account.getAccountNumber(), account.getAccountNumber());
        return transactions.stream()
                .map(transaction -> {
                    if(transaction.getFrom().equals(account) && !transaction.getTo().equals(account) && transaction.getOperationType().equals(OperationType.TRANSFER)){
                        return TransactionMapper.map(transaction,  Name.getName(transaction.getTo(), getBranchByAccount(transaction.getTo())), true);
                    }
                    else if (transaction.getTo().equals(account) && transaction.getFrom().equals(account) && !transaction.getOperationType().equals(OperationType.DEPOSIT)) {
                        return TransactionMapper.map(transaction, iSeeMee, true);
                    }
                    else if(!transaction.getFrom().equals(account) && transaction.getTo().equals(account) && transaction.getOperationType().equals(OperationType.TRANSFER)){
                        return TransactionMapper.map(transaction,  Name.getName(transaction.getFrom(), getBranchByAccount(transaction.getFrom())), false);
                    }

                    return TransactionMapper.map(transaction, iSeeMee, false);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addUser(String tokenValue, UserDTO userDTO) {
        xSync.execute(userDTO.getIdCardDTO().getNumber(), () -> {
            Account account = getAccountByAccountNumber(userDTO.getAccountNumber());
            oAuthService.authorize(tokenValue, account);
            User user = UserMapper.map(userDTO);
            user.getIdCard().setFirstName(user.getFirstName());
            user.getIdCard().setLastName(user.getLastName());
            hashIdCard(user.getIdCard());
            throwIfUserExist(user.getIdCard().getNumber());
            transferLimitRepository.save(account.getTransferLimit());
            idCardRepository.save(user.getIdCard());
            userRepository.save(user);
            account.addUser(user);
            user.setPassword(encryptionService.encrypt(user.getPassword()));
            creditCardRepository.saveAndFlush(account.getCreditCard());
            accountRepository.save(account);
        });
    }

    @Override
    public void createAccount(CreateAccountDTO createAccountDTO) {
        Branch branch = getBranchByBranchName(createAccountDTO.getBranchName());

        User user = UserMapper.map(createAccountDTO.getUserDTO());
        user.getIdCard().setFirstName(user.getFirstName());
        user.getIdCard().setLastName(user.getLastName());
        hashIdCard(user.getIdCard());
        Account account = AccountInitialize.initializeAccountInCreateStatus(user);
        throwIfUserExist(user.getIdCard().getNumber());
        user.setAccountOwner(true);
        hashCreditCard(account.getCreditCard());
        addUser(user, account);

        branch.addAccount(account);

        branchRepository.save(branch);
    }

    private void hashCreditCard(CreditCard creditCard){
        creditCard.setCcv(encryptionService.encrypt(creditCard.getCcv()));
        creditCard.setFirstName(encryptionService.encrypt(creditCard.getFirstName()));
        creditCard.setLastName(encryptionService.encrypt(creditCard.getLastName()));
        creditCard.setNumber(encryptionService.encrypt(creditCard.getNumber()));
    }

    @Override
    public DetailsDTO getDetails(String tokenValue) {
        Account account = getAccountByTokenValue(tokenValue);
        return DetailsMapper.map(account);
    }

    private void addUser(User user, Account account) {
        throwIfUserExist(user.getIdCard().getNumber());
        user.setPassword(encryptionService.encrypt(user.getPassword()));
        account.addUser(user);
        transferLimitRepository.save(account.getTransferLimit());
        idCardRepository.saveAndFlush(user.getIdCard());
        creditCardRepository.saveAndFlush(account.getCreditCard());
        userRepository.save(user);

        accountRepository.save(account);
    }

    private void throwIfUserExist(String number) {
        Optional<User> user = userRepository.getUserByIdCard_Number(number);

        if (user.isPresent()) {
            throw new BankException(ExceptionCode.USER_ALREADY_EXIST);
        }
    }

    private Branch getBranchByBranchName(String branchName) {
        return branchRepository
                .findBranchByBranchName(branchName)
                .orElseThrow(() -> new BankException(ExceptionCode.BRANCH_NOT_FOUND));
    }

    private Branch getBranchByAccount(Account account){
        return branchRepository.findBranchByAccountsContains(account).orElseThrow(() -> new BankException(ExceptionCode.BRANCH_NOT_FOUND));
    }

    private Account getAccountByAccountNumber(String accountNumber) throws BankException {
        return accountRepository
                .findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankException(ExceptionCode.ACCOUNT_NOT_FOUND));
    }

    private Account getAccountByTokenValue(String tokenValue) {
        User user = tokenRepository.findTokenByValue(tokenValue).orElseThrow(() -> new BankException(ExceptionCode.USER_NOT_FOUND)).getUser();
        return accountRepository.findAll().stream().filter(account -> account.getUsers().contains(user)).findFirst().orElseThrow(() -> new BankException(ExceptionCode.ACCOUNT_NOT_FOUND));
    }

    private void hashIdCard(IdCard idCard) {
        idCard.setFirstName(encryptionService.encrypt(idCard.getFirstName()));
        idCard.setLastName(encryptionService.encrypt(idCard.getLastName()));
        idCard.setNumber(encryptionService.encrypt(idCard.getNumber()));
        idCard.setBirthDate(encryptionService.encrypt(idCard.getBirthDate()));
        idCard.setMotherFirstName(encryptionService.encrypt(idCard.getMotherFirstName()));
        idCard.setFatherFirstName(encryptionService.encrypt(idCard.getFatherFirstName()));
    }
}
