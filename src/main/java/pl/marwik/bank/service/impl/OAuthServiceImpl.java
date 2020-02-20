package pl.marwik.bank.service.impl;

import com.antkorwin.xsync.XSync;
import org.springframework.stereotype.Service;
import pl.marwik.bank.exception.BankException;
import pl.marwik.bank.exception.ExceptionCode;
import pl.marwik.bank.mapper.TokenMapper;
import pl.marwik.bank.model.entity.Token;
import pl.marwik.bank.model.entity.User;
import pl.marwik.bank.model.request.LoginDTO;
import pl.marwik.bank.repository.TokenRepository;
import pl.marwik.bank.repository.UserRepository;
import pl.marwik.bank.service.OAuthService;

@Service
public class OAuthServiceImpl implements OAuthService {
    private TokenRepository tokenRepository;
    private UserRepository userRepository;
    private XSync<String> xSync;

    public OAuthServiceImpl(TokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.xSync = new XSync<>();
    }

    @Override
    public String login(LoginDTO loginDTO) throws BankException {
        return xSync.evaluate(loginDTO.getLogin(), () -> {
            User user = getUserByLogin(loginDTO.getLogin());
            throwIfCredentialsNotMatch(loginDTO.getLogin(), loginDTO.getPassword());
            throwIfLoggedIn(loginDTO.getLogin());

            Token token = TokenMapper.map(loginDTO.getClient(), user);

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
        getTokenByTokenValue(tokenValue);
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
        tokenRepository
                .findTokenByValue(login)
                .orElseThrow(() -> new BankException(ExceptionCode.USER_ALREADY_LOGGED_IN));
    }
}
