package pl.marwik.bank.service;

import pl.marwik.bank.model.request.TransactionTransferSelfDTO;
import pl.marwik.bank.model.request.TransactionTransferDTO;

public interface TransactionService {
    void transfer(TransactionTransferDTO transactionTransferDTO);
    void deposit(TransactionTransferSelfDTO transactionTransferSelfDTO);
    void withdraw(TransactionTransferSelfDTO transactionTransferSelfDTO);
}
