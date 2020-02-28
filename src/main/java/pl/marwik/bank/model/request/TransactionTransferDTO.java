package pl.marwik.bank.model.request;

import pl.marwik.bank.model.Client;
import pl.marwik.bank.model.OperationType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class TransactionTransferDTO implements TransactionDTO{
    private String senderAccountNumber;
    private String recipientAccountNumber;
    @DecimalMin(value = "0.01")
    private BigDecimal amount;
    private String title;
    @Enumerated(EnumType.STRING)
    private Client client;
    private BigDecimal senderBalance;
    private OperationType operationType;

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public String getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    public void setRecipientAccountNumber(String recipientAccountNumber) {
        this.recipientAccountNumber = recipientAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getSenderBalance() {
        return senderBalance;
    }

    @Override
    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public void setSenderBalance(BigDecimal senderBalance) {
        this.senderBalance = senderBalance;
    }

    @Override
    public String toString() {
        return "TransactionTransferDTO{" +
                "senderAccountNumber='" + senderAccountNumber + '\'' +
                ", recipientAccountNumber='" + recipientAccountNumber + '\'' +
                ", amount=" + amount +
                ", description='" + title + '\'' +
                ", client=" + client +
                ", senderBalance=" + senderBalance +
                '}';
    }
}
