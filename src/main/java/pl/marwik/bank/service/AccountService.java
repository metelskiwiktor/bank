package pl.marwik.bank.service;

import pl.marwik.bank.model.request.CreateAccountDTO;
import pl.marwik.bank.model.request.UserDTO;
import pl.marwik.bank.model.response.TransactionDTO;

import java.util.List;

public interface AccountService {
    List<TransactionDTO> getHistory(String tokenValue);
    void addUser(String tokenValue, UserDTO userDTO);
    void createAccount(CreateAccountDTO createAccountDTO);
}
