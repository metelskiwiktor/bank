package pl.marwik.bank.model.request;

import pl.marwik.bank.model.Client;
import pl.marwik.bank.model.OperationType;
import pl.marwik.bank.model.helper.TransferMoney;

import java.math.BigDecimal;

public class TransactionTransferSelfDTO implements TransactionDTO{
    private Long id;
    private BigDecimal amount;
    private String senderAccountNumber;
    private BigDecimal senderBalance;
    private Client client;
    private OperationType operationType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    @Override
    public BigDecimal getSenderBalance() {
        return senderBalance;
    }

    public void setSenderBalance(BigDecimal senderBalance) {
        this.senderBalance = senderBalance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public OperationType getOperationType() {
        return operationType;
    }
}
