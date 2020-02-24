package pl.marwik.bank.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pl.marwik.bank.model.entity.Transaction;
import pl.marwik.bank.model.oauth.RequireUserAuthenticate;
import pl.marwik.bank.model.request.CreateAccountDTO;
import pl.marwik.bank.model.request.UserDTO;
import pl.marwik.bank.model.response.TransactionDTO;
import pl.marwik.bank.service.AccountService;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequireUserAuthenticate
    @PostMapping("/add-user")
    public void addUser(@RequestHeader("tokenValue") String tokenValue, @RequestBody UserDTO userDTO) {
        accountService.addUser(tokenValue, userDTO);
    }

    @PostMapping("/create-account")
    public void createAccount(@RequestBody CreateAccountDTO createAccountDTO) {
        this.accountService.createAccount(createAccountDTO);
    }

    @RequireUserAuthenticate
    @GetMapping("/transactions-history")
    public Page<TransactionDTO> getHistory(
            @RequestHeader("tokenValue") String tokenValue,
            @RequestHeader("accountNumber") String accountNumber,
            @RequestParam("amount") BigDecimal amount) {
        return accountService.getHistory(tokenValue, accountNumber, amount);
    }
}
