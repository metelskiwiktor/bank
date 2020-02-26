package pl.marwik.bank.model.response;

import java.math.BigDecimal;

public class DetailsDTO {
    private BigDecimal balance;
    private String accountNumber;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
