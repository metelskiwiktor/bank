package pl.marwik.bank.service;

import pl.marwik.bank.exception.BankException;
import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.request.LoginDTO;

public interface OAuthService {
    String login(LoginDTO loginDTO);
    void logout(String tokenValue);
    void throwIfTokenIsInvalid(String tokenValue);
    void throwIfTokenIsNotAdmin(String tokenValue) throws BankException;
    void authorize(String tokenValue, Account owner);
}
