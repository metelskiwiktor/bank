package pl.marwik.bank.model.response.account;

import pl.marwik.bank.model.AccountStatus;
import pl.marwik.bank.model.entity.CreditCard;
import pl.marwik.bank.model.entity.TransferLimit;

import java.math.BigDecimal;
import java.util.Set;

public class AccountDTO {
    private Set<UserDTO> users;
    private String accountNumber;
    private BigDecimal balance;
    private AccountStatus accountStatus;
    private CreditCardDTO creditCard;
    private TransferLimit transferLimit;

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public CreditCardDTO getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardDTO creditCard) {
        this.creditCard = creditCard;
    }

    public TransferLimit getTransferLimit() {
        return transferLimit;
    }

    public void setTransferLimit(TransferLimit transferLimit) {
        this.transferLimit = transferLimit;
    }
}
