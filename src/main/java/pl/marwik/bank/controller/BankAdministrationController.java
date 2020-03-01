package pl.marwik.bank.controller;

import org.springframework.web.bind.annotation.*;
import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.oauth.RequireAdminAuthenticate;
import pl.marwik.bank.model.request.CreateBankDTO;
import pl.marwik.bank.model.request.CreateBranchDTO;
import pl.marwik.bank.model.response.account.AccountDTO;
import pl.marwik.bank.service.AccountService;
import pl.marwik.bank.service.BankService;
import pl.marwik.bank.service.BranchService;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201", "http://localhost:4202", "http://localhost:4203"})
@RestController
@RequestMapping("/bank-administration")
public class BankAdministrationController {
    private BankService bankService;
    private BranchService branchService;
    private AccountService accountService;

    public BankAdministrationController(BankService bankService, BranchService branchService, AccountService accountService) {
        this.bankService = bankService;
        this.branchService = branchService;
        this.accountService = accountService;
    }

//    @RequireAdminAuthenticate
    @PostMapping(value = "/create-bank", consumes = "application/json")
    public void createBank(@RequestHeader("tokenValue") String tokenValue, @RequestBody CreateBankDTO createBankDTO) {
        this.bankService.addBank(createBankDTO);
    }


    @GetMapping("/{accountNumber}/branch")
    public String getBranchName(@PathVariable String accountNumber) {
        return branchService.getBranchName(accountNumber);
    }

//    @RequireAdminAuthenticate
    @PostMapping(value = "/create-branch", consumes = "application/json")
    public void createBranch(@RequestBody CreateBranchDTO createBranchDTO){
        branchService.createBranch(createBranchDTO);
    }

//    @RequireAdminAuthenticate
    @GetMapping("/creating-list")
    public List<AccountDTO> accountsInCreatingStatus(@RequestHeader("tokenValue") String tokenValue) {
        return accountService.accountInCreatingStatus();
    }

//    @RequireAdminAuthenticate
    @GetMapping("/valid/{accountNumber}")
    public void makeAccountValid(@PathVariable String accountNumber) {
        accountService.makeAccountValid(accountNumber);
    }
}
