package pl.marwik.bank.service.impl;

import com.antkorwin.xsync.XSync;
import org.springframework.stereotype.Service;
import pl.marwik.bank.exception.BankException;
import pl.marwik.bank.exception.ExceptionCode;
import pl.marwik.bank.initializer.TokenInitialize;
import pl.marwik.bank.model.AccountStatus;
import pl.marwik.bank.model.Role;
import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.entity.Token;
import pl.marwik.bank.model.entity.User;
import pl.marwik.bank.model.request.LoginDTO;
import pl.marwik.bank.repository.AccountRepository;
import pl.marwik.bank.repository.TokenRepository;
import pl.marwik.bank.repository.UserRepository;
import pl.marwik.bank.service.OAuthService;

import java.util.Objects;
import java.util.Optional;

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
    public String login(LoginDTO loginDTO) throws BankException {
        return xSync.evaluate(loginDTO.getLogin(), () -> {
            User user = getUserByLogin(loginDTO.getLogin());
            throwIfUserIsNotValid(user);
            throwIfCredentialsNotMatch(loginDTO.getLogin(), loginDTO.getPassword());
            throwIfLoggedIn(loginDTO.getLogin());

            Token token = TokenInitialize.initializeRandomTokenForUserRole(loginDTO.getClient(), user);

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
    public void throwIfTokenIsInvalid(String tokenValue) throws BankException {
        Token tokenByTokenValue = getTokenByTokenValue(tokenValue);
        throwIfUserIsNotValid(tokenByTokenValue.getUser());
    }

    @Override
    public void throwIfTokenIsNotAdmin(String tokenValue) throws BankException{
        throwIfTokenIsInvalid(tokenValue);
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

    private void throwIfCredentialsNotMatch(String login, String password){
        userRepository
                .getUserByLoginAndPassword(login, password)
                .orElseThrow(() -> new BankException(ExceptionCode.INVALID_CREDENTIALS));
    }

    private void throwIfLoggedIn(String login) {
        Optional<Token> token = tokenRepository
                .findTokenByValue(login);

        if(token.isPresent()){
            throw new BankException(ExceptionCode.USER_ALREADY_LOGGED_IN);
        }
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
