package pl.marwik.bank.service;

import org.springframework.data.domain.Page;
import pl.marwik.bank.model.entity.Transaction;
import pl.marwik.bank.model.request.CreateAccountDTO;
import pl.marwik.bank.model.request.UserDTO;

import java.math.BigDecimal;
import java.util.Date;

public interface AccountService {
    Page<Transaction> getHistory(String accountNumber, Date dateFrom, Date dateTo, BigDecimal amount);
    void addUser(UserDTO userDTO);
    void createAccount(CreateAccountDTO createAccountDTO);
}
