package pl.marwik.bank.service;

import pl.marwik.bank.model.request.TransactionTransferSelfDTO;
import pl.marwik.bank.model.request.TransactionTransferDTO;

public interface TransactionService {
    void transfer(String tokenValue, TransactionTransferDTO transactionTransferDTO);
    void deposit(String tokenValue, TransactionTransferSelfDTO transactionTransferSelfDTO);
    void withdraw(String tokenValue, TransactionTransferSelfDTO transactionTransferSelfDTO);
}
