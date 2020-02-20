package pl.marwik.bank.controller;

import org.springframework.web.bind.annotation.*;
import pl.marwik.bank.model.request.CreateBankDTO;
import pl.marwik.bank.service.BankService;

@RestController
@RequestMapping("/bank")
public class BankController {
    private BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping(value = "/create-bank", consumes = "application/json")
    public void createBank(@RequestBody CreateBankDTO createBankDTO) {
        this.bankService.addBank(createBankDTO);
    }
}
