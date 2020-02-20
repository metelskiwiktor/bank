package pl.marwik.bank.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.marwik.bank.model.entity.Transaction;
import pl.marwik.bank.model.request.CreateAccountDTO;
import pl.marwik.bank.model.request.UserDTO;
import pl.marwik.bank.service.AccountService;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/add-user")
    public void addUser(@RequestHeader("tokenValue") String tokenValue, @RequestBody UserDTO userDTO) {
        accountService.addUser(userDTO);
    }

    @PostMapping("/create-account")
    public void createAccount(@RequestBody CreateAccountDTO createAccountDTO) {
        this.accountService.createAccount(createAccountDTO);
    }

    @GetMapping("/transactions-history")
    public Page<Transaction> getHistory(
            @RequestHeader("tokenValue") String tokenValue,
            @RequestParam("accountNumber") String accountNumber,
            @RequestParam("dateFrom") Date dateFrom,
            @RequestParam("dateTo") Date dateTo,
            @RequestParam("amount") BigDecimal amount) {
        return accountService.getHistory(accountNumber, dateFrom, dateTo, amount);
    }
}
