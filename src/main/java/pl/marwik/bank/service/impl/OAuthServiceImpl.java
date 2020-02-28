package pl.marwik.bank.service.impl;

import com.antkorwin.xsync.XSync;
import org.springframework.stereotype.Service;
import pl.marwik.bank.exception.BankException;
import pl.marwik.bank.exception.ExceptionCode;
import pl.marwik.bank.initializer.TokenInitialize;
import pl.marwik.bank.model.AccountStatus;
import pl.marwik.bank.model.Client;
import pl.marwik.bank.model.Role;
import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.entity.Token;
import pl.marwik.bank.model.entity.User;
import pl.marwik.bank.model.request.login.CreditCardDTO;
import pl.marwik.bank.model.request.login.CredentialsDTO;
import pl.marwik.bank.model.request.login.IdCardDTO;
import pl.marwik.bank.repository.AccountRepository;
import pl.marwik.bank.repository.TokenRepository;
import pl.marwik.bank.repository.UserRepository;
import pl.marwik.bank.service.OAuthService;

import java.util.Objects;

@Service
public class OAuthServiceImpl implements OAuthService {
    private TokenRepository tokenRepository;
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private XSync<String> xSync;

    public OAuthServiceImpl(TokenRepository tokenRepository, UserRepository userRepository, AccountRepository accountRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.xSync = new XSync<>();
    }

    @Override
    public String login(CredentialsDTO credentialsDTO, String ipAddress) throws BankException {
        return xSync.evaluate(credentialsDTO.getLogin(), () -> {
            User user = getUserByLogin(credentialsDTO.getLogin());
            throwIfUserIsNotValid(user);
            throwIfCredentialsNotMatch(credentialsDTO.getLogin(), credentialsDTO.getPassword());

            deleteTokenIfLoggedIn(credentialsDTO.getLogin(), credentialsDTO.getClient());

            Token token = TokenInitialize.initializeRandomTokenForUserRole(credentialsDTO.getClient(), user, ipAddress);

            return tokenRepository.save(token).getValue();
        });
    }

    @Override
    public String login(IdCardDTO idCardDTO, String ipAddress) {
        User user = userRepository.getUserByIDCard(idCardDTO.getIdCard()).orElseThrow(() -> new BankException(ExceptionCode.USER_NOT_FOUND));
        return login(user, idCardDTO.getClient(), ipAddress);
    }

    @Override
    public String login(CreditCardDTO creditCardDTO, String ipAddress) {
        User user = getUserByCreditCard(creditCardDTO.getCardNumber());
        return login(user, creditCardDTO.getClient(), ipAddress);
    }

    private String login(User user, Client client, String ipAddress){
        return xSync.evaluate(user.getLogin(), () -> {
            throwIfUserIsNotValid(user);

            deleteTokenIfLoggedIn(user.getLogin(), client);

            Token token = TokenInitialize.initializeRandomTokenForUserRole(client, user, ipAddress);

            return tokenRepository.save(token).getValue();
        });
    }

    @Override
    public void logout(String tokenValue) throws BankException {
        xSync.execute(tokenValue, () -> {
            Token token = getTokenByTokenValue(tokenValue);

            tokenRepository.delete(token);
        });
    }

    @Override
    public void throwIfTokenIsInvalid(String tokenValue, String ipAddress) throws BankException {
        Token tokenByTokenValue = getTokenByTokenValue(tokenValue, ipAddress);
        throwIfUserIsNotValid(tokenByTokenValue.getUser());
    }

    @Override
    public void throwIfTokenIsNotAdmin(String tokenValue, String ipAddress) throws BankException{
        throwIfTokenIsInvalid(tokenValue, ipAddress);
        Role role = getTokenByTokenValue(tokenValue).getRole();
        if(!Objects.equals(role, Role.ADMINISTRATION)){
            throw new BankException(ExceptionCode.NOT_PERMITTED);
        }
    }

    @Override
    public void authorize(String tokenValue, Account owner){
        User ownerUser = tokenRepository.findTokenByValue(tokenValue).orElseThrow(() -> new BankException(ExceptionCode.USER_NOT_FOUND)).getUser();

        boolean isUserInAccount = owner.getUsers().stream().anyMatch(user -> user.equals(ownerUser));

        if(!isUserInAccount){
            throw new BankException(ExceptionCode.NOT_PERMITTED);
        }
    }

    private User getUserByCreditCard(String creditCard){
        return accountRepository.findAccountByCreditCard(creditCard)
                .orElseThrow(() -> new BankException(ExceptionCode.ACCOUNT_NOT_FOUND))
                .getUsers()
                .stream()
                .filter(User::isAccountOwner)
                .findFirst()
                .orElseThrow(() -> new BankException(ExceptionCode.USER_NOT_FOUND));
    }

    private User getUserByLogin(String login){
        return userRepository
                .getUserByLogin(login)
                .orElseThrow(() -> new BankException(ExceptionCode.USER_NOT_FOUND));
    }

    private Token getTokenByTokenValue(String tokenValue){
        return tokenRepository
                .findTokenByValue(tokenValue)
                .orElseThrow(() -> new BankException(ExceptionCode.TOKEN_NOT_FOUND));
    }

    private Token getTokenByTokenValue(String tokenValue, String ipAddress){
        return tokenRepository
                .findTokenByValueAndIpAddress(tokenValue, ipAddress)
                .orElseThrow(() -> new BankException(ExceptionCode.TOKEN_NOT_FOUND));
    }


    private void throwIfCredentialsNotMatch(String login, String password){
        userRepository
                .getUserByLoginAndPassword(login, password)
                .orElseThrow(() -> new BankException(ExceptionCode.INVALID_CREDENTIALS));
    }

    private void deleteTokenIfLoggedIn(String login, Client client) {
        tokenRepository
                .findTokenByUser_LoginAndClient(login, client)
                .ifPresent(tokenRepository::delete);
    }

    private void throwIfUserIsNotValid(User user){
        if(!getAccountByUser(user).getAccountStatus().equals(AccountStatus.VALID)){
            throw new BankException((ExceptionCode.ACCOUNT_IS_BLOCKED));
        }
    }

    private Account getAccountByUser(User user){
        return accountRepository
                .findAll()
                .stream()
                .filter(account -> account.getUsers().contains(user))
                .findFirst()
                .orElseThrow(() -> new BankException(ExceptionCode.ACCOUNT_NOT_FOUND));
    }
}
