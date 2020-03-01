package pl.marwik.bank.service;

import pl.marwik.bank.exception.BankException;
import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.request.login.CredentialsDTO;
import pl.marwik.bank.model.request.login.CreditCardDTO;
import pl.marwik.bank.model.request.login.IdCardDTO;

public interface OAuthService {
    String login(CreditCardDTO creditCardDTO);
    void logout(String tokenValue);
    void throwIfTokenIsInvalid(String tokenValue);
    void throwIfTokenIsNotAdmin(String tokenValue);
    void authorize(String tokenValue, Account owner);
    String login(CredentialsDTO credentialsDTO) throws BankException;
    String login(IdCardDTO idCardDTO);
}
