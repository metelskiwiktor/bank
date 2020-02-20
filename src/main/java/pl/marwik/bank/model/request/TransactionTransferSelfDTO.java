package pl.marwik.bank.model.request;

import pl.marwik.bank.model.helper.TransferMoney;

import java.math.BigDecimal;

public class TransactionTransferSelfDTO implements TransactionDTO{
    private Long id;
    private BigDecimal amount;
    private String senderAccountNumber;
    private TransferMoney transferMoney;
    private BigDecimal senderBalance;

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

    public TransferMoney getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(TransferMoney transferMoney) {
        this.transferMoney = transferMoney;
    }

    @Override
    public BigDecimal getSenderBalance() {
        return senderBalance;
    }

    public void setSenderBalance(BigDecimal senderBalance) {
        this.senderBalance = senderBalance;
    }
}
