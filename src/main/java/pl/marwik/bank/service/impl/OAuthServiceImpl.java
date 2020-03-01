package pl.marwik.bank.service.impl;

import com.antkorwin.xsync.XSync;
import org.springframework.stereotype.Service;
import pl.marwik.bank.exception.BankException;
import pl.marwik.bank.exception.ExceptionCode;
import pl.marwik.bank.initializer.TokenInitialize;
import pl.marwik.bank.mapper.CreditCardMapper;
import pl.marwik.bank.mapper.IdCardMapper;
import pl.marwik.bank.model.AccountStatus;
import pl.marwik.bank.model.Client;
import pl.marwik.bank.model.Role;
import pl.marwik.bank.model.entity.*;
import pl.marwik.bank.model.request.login.CreditCardDTO;
import pl.marwik.bank.model.request.login.CredentialsDTO;
import pl.marwik.bank.model.request.login.IdCardDTO;
import pl.marwik.bank.repository.AccountRepository;
import pl.marwik.bank.repository.TokenRepository;
import pl.marwik.bank.repository.UserRepository;
import pl.marwik.bank.service.OAuthService;
import pl.marwik.bank.service.oauth.EncryptionServiceImpl;

import java.util.Objects;

@Service
public class OAuthServiceImpl implements OAuthService {
    private TokenRepository tokenRepository;
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private XSync<String> xSync;
    private EncryptionServiceImpl encryptionService;

    public OAuthServiceImpl(TokenRepository tokenRepository, UserRepository userRepository, AccountRepository accountRepository, EncryptionServiceImpl encryptionService) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.encryptionService = encryptionService;
        this.xSync = new XSync<>();
    }

    @Override
    public String login(CredentialsDTO credentialsDTO) throws BankException {
        return xSync.evaluate(credentialsDTO.getLogin(), () -> {
            credentialsDTO.setPassword(encryptionService.encrypt(credentialsDTO.getPassword()));
            User user = getUserByLogin(credentialsDTO.getLogin());
            throwIfUserIsNotValid(user);
            throwIfCredentialsNotMatch(credentialsDTO.getLogin(), credentialsDTO.getPassword());

            deleteTokenIfLoggedIn(credentialsDTO.getLogin(), credentialsDTO.getClient());

            Token token = TokenInitialize.initializeRandomTokenForUserRole(credentialsDTO.getClient(), user);

            return tokenRepository.save(token).getValue();
        });
    }

    @Override
    public String login(IdCardDTO idCardDTO) {
        hashIdCard(idCardDTO);
        IdCard idCard = IdCardMapper.map(idCardDTO);
        User user = userRepository.getUserByIdCard(idCard).orElseThrow(() -> new BankException(ExceptionCode.USER_NOT_FOUND));
        return login(user, idCardDTO.getClient());
    }

    @Override
    public String login(CreditCardDTO creditCardDTO) {
        hashCreditCard(creditCardDTO);
        CreditCard creditCard = CreditCardMapper.map(creditCardDTO);
        User user = getUserByCreditCard(creditCard);
        return login(user, creditCardDTO.getClient());
    }

    @Override
    public void logout(String tokenValue) throws BankException {
        xSync.execute(tokenValue, () -> {
            Token token = getTokenByTokenValue(tokenValue);

            tokenRepository.delete(token);
        });
    }

    @Override
    public void throwIfTokenIsInvalid(String tokenValue) throws BankException {
        Token tokenByTokenValue = getTokenByTokenValue(tokenValue);
        throwIfUserIsNotValid(tokenByTokenValue.getUser());
    }

    @Override
    public void throwIfTokenIsNotAdmin(String tokenValue) throws BankException {
        throwIfTokenIsInvalid(tokenValue);
        Role role = getTokenByTokenValue(tokenValue).getRole();
        if (!Objects.equals(role, Role.ADMINISTRATION)) {
            throw new BankException(ExceptionCode.NOT_PERMITTED);
        }
    }

    @Override
    public void authorize(String tokenValue, Account owner) {
        User ownerUser = tokenRepository.findTokenByValue(tokenValue).orElseThrow(() -> new BankException(ExceptionCode.USER_NOT_FOUND)).getUser();

        boolean isUserInAccount = owner.getUsers().stream().anyMatch(user -> user.equals(ownerUser));

        if (!isUserInAccount) {
            throw new BankException(ExceptionCode.NOT_PERMITTED);
        }
    }

    private String login(User user, Client client) {
        return xSync.evaluate(user.getLogin(), () -> {
            throwIfUserIsNotValid(user);

            deleteTokenIfLoggedIn(user.getLogin(), client);

            Token token = TokenInitialize.initializeRandomTokenForUserRole(client, user);

            return tokenRepository.save(token).getValue();
        });
    }

    private User getUserByCreditCard(CreditCard creditCard) {
        return accountRepository.findAccountByCreditCard_NumberAndCreditCard_CcvAndCreditCard_ExpiryDateAndCreditCard_FirstNameAndCreditCard_LastName(
                creditCard.getNumber(),
                creditCard.getCcv(),
                creditCard.getExpiryDate(),
                creditCard.getFirstName(),
                creditCard.getLastName())
                .orElseThrow(() -> new BankException(ExceptionCode.ACCOUNT_NOT_FOUND))
                .getUsers()
                .stream()
                .filter(User::isAccountOwner)
                .findFirst()
                .orElseThrow(() -> new BankException(ExceptionCode.USER_NOT_FOUND));
    }

    private User getUserByLogin(String login) {
        return userRepository
                .getUserByLogin(login)
                .orElseThrow(() -> new BankException(ExceptionCode.USER_NOT_FOUND));
    }

    private Token getTokenByTokenValue(String tokenValue) {
        return tokenRepository
                .findTokenByValue(tokenValue)
                .orElseThrow(() -> new BankException(ExceptionCode.TOKEN_NOT_FOUND));
    }

    private void throwIfCredentialsNotMatch(String login, String password) {
        userRepository
                .getUserByLoginAndPassword(login, password)
                .orElseThrow(() -> new BankException(ExceptionCode.INVALID_CREDENTIALS));
    }

    private void deleteTokenIfLoggedIn(String login, Client client) {
        tokenRepository
                .findTokenByUser_LoginAndClient(login, client)
                .ifPresent(tokenRepository::delete);
    }

    private void throwIfUserIsNotValid(User user) {
        if (!getAccountByUser(user).getAccountStatus().equals(AccountStatus.VALID)) {
            throw new BankException((ExceptionCode.ACCOUNT_IS_BLOCKED));
        }
    }

    private Account getAccountByUser(User user) {
        return accountRepository
                .findAll()
                .stream()
                .filter(account -> account.getUsers().contains(user))
                .findFirst()
                .orElseThrow(() -> new BankException(ExceptionCode.ACCOUNT_NOT_FOUND));
    }

    private void hashCreditCard(CreditCardDTO creditCard) {
        creditCard.setCcv(encryptionService.encrypt(creditCard.getCcv()));
        creditCard.setFirstName(encryptionService.encrypt(creditCard.getFirstName()));
        creditCard.setLastName(encryptionService.encrypt(creditCard.getLastName()));
        creditCard.setNumber(encryptionService.encrypt(creditCard.getNumber()));
    }


    private void hashIdCard(IdCardDTO idCardDTO) {
        idCardDTO.setFirstName(encryptionService.encrypt(idCardDTO.getFirstName()));
        idCardDTO.setLastName(encryptionService.encrypt(idCardDTO.getLastName()));
        idCardDTO.setNumber(encryptionService.encrypt(idCardDTO.getNumber()));
        idCardDTO.setBirthDate(encryptionService.encrypt(idCardDTO.getBirthDate()));
        idCardDTO.setMotherFirstName(encryptionService.encrypt(idCardDTO.getMotherFirstName()));
        idCardDTO.setFatherFirstName(encryptionService.encrypt(idCardDTO.getFatherFirstName()));
    }
}
