package pl.marwik.bank.controller;

import org.springframework.web.bind.annotation.*;
import pl.marwik.bank.model.oauth.RequireUserAuthenticate;
import pl.marwik.bank.model.request.TransactionTransferDTO;
import pl.marwik.bank.model.request.TransactionTransferSelfDTO;
import pl.marwik.bank.service.TransactionService;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201", "http://localhost:4202", "http://localhost:4203"})
@RestController
@RequestMapping("/transaction/")
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequireUserAuthenticate
    @PostMapping("/transfer")
    public void transferMoney(@RequestHeader("tokenValue") String tokenValue, @RequestBody TransactionTransferDTO transactionTransferDTO){
        this.transactionService.transfer(tokenValue, transactionTransferDTO);
    }

    @RequireUserAuthenticate
    @PostMapping("/deposit")
    public void deposit(@RequestHeader("tokenValue") String tokenValue, @RequestBody TransactionTransferSelfDTO transactionTransferSelfDTO){
        this.transactionService.deposit(tokenValue, transactionTransferSelfDTO);
    }

    @RequireUserAuthenticate
    @PostMapping("/withdraw")
    public void withdraw(@RequestHeader("tokenValue") String tokenValue, @RequestBody TransactionTransferSelfDTO transactionTransferSelfDTO){
        this.transactionService.withdraw(tokenValue, transactionTransferSelfDTO);
    }
}
