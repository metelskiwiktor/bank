package pl.marwik.bank.service;

import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.request.CreateAccountDTO;
import pl.marwik.bank.model.request.UserDTO;
import pl.marwik.bank.model.response.DetailsDTO;
import pl.marwik.bank.model.response.TransactionDTO;
import pl.marwik.bank.model.response.account.AccountDTO;

import java.util.List;

public interface AccountService {
    List<TransactionDTO> getHistory(String tokenValue);
    void addUser(String tokenValue, UserDTO userDTO);
    void createAccount(CreateAccountDTO createAccountDTO);
    DetailsDTO getDetails(String tokenValue);
    List<AccountDTO> accountInCreatingStatus();
    void makeAccountValid(String accountNumber);
}
