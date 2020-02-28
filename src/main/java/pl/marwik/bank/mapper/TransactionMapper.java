package pl.marwik.bank.mapper;

import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.entity.Transaction;
import pl.marwik.bank.model.helper.TransferMoney;
import pl.marwik.bank.model.request.TransactionTransferDTO;
import pl.marwik.bank.model.request.TransactionTransferSelfDTO;
import pl.marwik.bank.model.response.TransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class TransactionMapper {
    public static Transaction map(TransactionTransferDTO transactionTransferDTO){
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionTransferDTO.getAmount());
        transaction.setClient(transactionTransferDTO.getClient());
        transaction.setTitle(transactionTransferDTO.getTitle());
        transaction.setClient(transactionTransferDTO.getClient());
        return transaction;
    }

    public static Transaction map(TransactionTransferDTO transactionTransferDTO, Account from, Account to, LocalDateTime transactionDate){
        Transaction transaction = map(transactionTransferDTO);
        transaction.setFrom(from);
        transaction.setTo(to);
        transaction.setActionTime(transactionDate);
        transaction.setFromBalance(from.getBalance());
        transaction.setToBalance(to.getBalance());
        transaction.setOperationType(transactionTransferDTO.getOperationType());

        return transaction;
    }

    public static Transaction map(TransactionTransferSelfDTO transactionTransferSelfDTO, Account account, LocalDateTime localDateTime){
        Transaction transaction = new Transaction();
        transaction.setActionTime(localDateTime);
        transaction.setClient(transactionTransferSelfDTO.getClient());
        transaction.setTo(account);
        transaction.setFrom(account);
        transaction.setFromBalance(account.getBalance());
        transaction.setToBalance(account.getBalance());
        transaction.setTitle(transactionTransferSelfDTO.getClient().getDescription());
        transaction.setAmount(transactionTransferSelfDTO.getAmount());
        transaction.setOperationType(transactionTransferSelfDTO.getOperationType());

        return transaction;
    }

    public static TransactionDTO map(Transaction transaction, String fromTo, boolean sender){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setActionTime(transaction.getActionTime());
        transactionDTO.setAmount(transaction.getAmount());

        BigDecimal balance;
        if(sender){
            balance = TransferMoney.SENDER.transfer(transaction.getFromBalance(), transaction.getAmount());
        } else {
            balance = TransferMoney.RECIPIENT.transfer(transaction.getToBalance(), transaction.getAmount());
        }
        transactionDTO.setBalanceAfter(balance);
        transactionDTO.setTitle(transaction.getTitle());
        transactionDTO.setOperationType(transaction.getOperationType());
        transactionDTO.setFromTo(fromTo);
        return transactionDTO;
    }
}
