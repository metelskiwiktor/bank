package pl.marwik.bank.controller;

import org.springframework.web.bind.annotation.*;
import pl.marwik.bank.model.oauth.RequireAdminAuthenticate;
import pl.marwik.bank.model.request.CreateBankDTO;
import pl.marwik.bank.model.request.CreateBranchDTO;
import pl.marwik.bank.service.BankService;
import pl.marwik.bank.service.BranchService;

@RestController
@RequestMapping("/bank-administration")
public class BankAdministrationController {
    private BankService bankService;
    private BranchService branchService;

    public BankAdministrationController(BankService bankService, BranchService branchService) {
        this.bankService = bankService;
        this.branchService = branchService;
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
}
