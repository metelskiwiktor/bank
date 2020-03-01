package pl.marwik.bank.model.entity;

import pl.marwik.bank.model.Client;
import pl.marwik.bank.model.OperationType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime actionTime;
    @OneToOne
    private Account from;
    @OneToOne
    private Account to;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private Client client;
    private BigDecimal fromBalance;
    private BigDecimal toBalance;
    private String title;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getActionTime() {
        return actionTime;
    }

    public void setActionTime(LocalDateTime actionTime) {
        this.actionTime = actionTime;
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getFromBalance() {
        return fromBalance;
    }

    public void setFromBalance(BigDecimal fromBalance) {
        this.fromBalance = fromBalance;
    }

    public BigDecimal getToBalance() {
        return toBalance;
    }

    public void setToBalance(BigDecimal toBalance) {
        this.toBalance = toBalance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(actionTime, that.actionTime) &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(amount, that.amount) &&
                client == that.client &&
                Objects.equals(fromBalance, that.fromBalance) &&
                Objects.equals(toBalance, that.toBalance) &&
                Objects.equals(title, that.title) &&
                operationType == that.operationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(actionTime, from, to, amount, client, fromBalance, toBalance, title, operationType);
    }
}
