package pl.marwik.bank.service;

import pl.marwik.bank.exception.BankException;
import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.request.login.CredentialsDTO;
import pl.marwik.bank.model.request.login.CreditCardDTO;
import pl.marwik.bank.model.request.login.IdCardDTO;

public interface OAuthService {
    String login(CreditCardDTO creditCardDTO, String ipAddress);
    void logout(String tokenValue);
    void throwIfTokenIsInvalid(String tokenValue, String ipAddress);
    void throwIfTokenIsNotAdmin(String tokenValue, String ipAddress);
    void authorize(String tokenValue, Account owner);

    String login(CredentialsDTO credentialsDTO, String ipAddress) throws BankException;

    String login(IdCardDTO idCardDTO, String ipAddress);
}
