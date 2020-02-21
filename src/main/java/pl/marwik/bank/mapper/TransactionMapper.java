package pl.marwik.bank.mapper;

import pl.marwik.bank.model.Client;
import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.entity.Transaction;
import pl.marwik.bank.model.request.TransactionTransferDTO;
import pl.marwik.bank.model.request.TransactionTransferSelfDTO;

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

    public static Transaction map(TransactionTransferSelfDTO transactionTransferSelfDTO, Account account, LocalDateTime localDateTime){
        Transaction transaction = new Transaction();
        transaction.setLocalDateTime(localDateTime);
        transaction.setClient(transactionTransferSelfDTO.getClient());
        transaction.setTo(account);
        transaction.setFrom(account);
        transaction.setDescription(transactionTransferSelfDTO.getClient().getDescription());
        transaction.setAmount(transactionTransferSelfDTO.getAmount());

        return transaction;
    }
}
