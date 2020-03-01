package pl.marwik.bank.model.entity;

import pl.marwik.bank.model.AccountStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany
    private Set<User> users;
    @Column(unique = true)
    private String accountNumber;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    @OneToOne
    private CreditCard creditCard;
    @OneToOne
    private TransferLimit transferLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
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

    public void addUser(User user){
        if(users == null){
            users = new LinkedHashSet<>();
        }

        users.add(user);
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(users, account.users) &&
                Objects.equals(accountNumber, account.accountNumber) &&
                Objects.equals(balance, account.balance) &&
                accountStatus == account.accountStatus &&
                Objects.equals(creditCard, account.creditCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, accountNumber, balance, accountStatus, creditCard);
    }

    public TransferLimit getTransferLimit() {
        return transferLimit;
    }

    public void setTransferLimit(TransferLimit transferLimit) {
        this.transferLimit = transferLimit;
    }
}
