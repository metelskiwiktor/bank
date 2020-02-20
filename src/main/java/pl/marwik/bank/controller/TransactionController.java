package pl.marwik.bank.controller;

import org.springframework.web.bind.annotation.*;
import pl.marwik.bank.model.request.TransactionTransferDTO;
import pl.marwik.bank.model.request.TransactionTransferSelfDTO;
import pl.marwik.bank.service.TransactionService;

@RestController
@RequestMapping("/transaction/")
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public void transferMoney(@RequestHeader("tokenValue") String tokenValue, @RequestBody TransactionTransferDTO transactionTransferDTO){
        this.transactionService.transfer(transactionTransferDTO);
    }

    @PostMapping("/deposit")
    public void deposit(@RequestHeader("tokenValue") String tokenValue, @RequestBody TransactionTransferSelfDTO transactionTransferSelfDTO){
        this.transactionService.deposit(transactionTransferSelfDTO);
    }

    @PostMapping("/withdraw")
    public void withdraw(@RequestHeader("tokenValue") String tokenValue, @RequestBody TransactionTransferSelfDTO transactionTransferSelfDTO){
        this.transactionService.withdraw(transactionTransferSelfDTO);
    }
}
