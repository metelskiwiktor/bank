package pl.marwik.bank.mapper;

import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.entity.Transaction;
import pl.marwik.bank.model.request.TransactionTransferDTO;

import java.time.LocalDateTime;

public abstract class TransactionMapper {
    public static Transaction map(TransactionTransferDTO transactionTransferDTO){
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionTransferDTO.getAmount());
        transaction.setClient(transactionTransferDTO.getClient());
        transaction.setDescription(transactionTransferDTO.getDescription());
        transaction.setClient(transactionTransferDTO.getClient());

        return transaction;
    }

    public static Transaction map(TransactionTransferDTO transactionTransferDTO, Account from, Account to, LocalDateTime transactionDate){
        Transaction transaction = map(transactionTransferDTO);
        transaction.setFrom(from);
        transaction.setTo(to);
        transaction.setLocalDateTime(transactionDate);
        return transaction;
    }
}
