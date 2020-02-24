package pl.marwik.bank.service;

import org.springframework.data.domain.Page;
import pl.marwik.bank.model.entity.Transaction;
import pl.marwik.bank.model.request.CreateAccountDTO;
import pl.marwik.bank.model.request.UserDTO;
import pl.marwik.bank.model.response.TransactionDTO;

import java.math.BigDecimal;

public interface AccountService {
    Page<TransactionDTO> getHistory(String tokenValue, String accountNumber, BigDecimal amount);
    void addUser(String tokenValue, UserDTO userDTO);
    void createAccount(CreateAccountDTO createAccountDTO);
}
