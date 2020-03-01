package pl.marwik.bank.controller;

import org.springframework.web.bind.annotation.*;
import pl.marwik.bank.model.oauth.RequireUserAuthenticate;
import pl.marwik.bank.model.request.CreateAccountDTO;
import pl.marwik.bank.model.request.UserDTO;
import pl.marwik.bank.model.response.DetailsDTO;
import pl.marwik.bank.model.response.TransactionDTO;
import pl.marwik.bank.service.AccountService;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201", "http://localhost:4202", "http://localhost:4203"})
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
    public List<TransactionDTO> getHistory(
            @RequestHeader("tokenValue") String tokenValue){
        return accountService.getHistory(tokenValue);
    }

    @RequireUserAuthenticate
    @GetMapping("/details")
    public DetailsDTO getDetails(@RequestHeader("tokenValue") String tokenValue){
        return accountService.getDetails(tokenValue);
    }
}
