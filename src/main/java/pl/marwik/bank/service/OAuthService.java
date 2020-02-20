package pl.marwik.bank.service;

import pl.marwik.bank.model.request.LoginDTO;

public interface OAuthService {
    String login(LoginDTO loginDTO);
    void logout(String tokenValue);
    void throwIfTokenIsInvalid(String tokenValue);
}
