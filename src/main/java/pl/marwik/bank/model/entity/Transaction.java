package pl.marwik.bank.model.entity;

import pl.marwik.bank.model.Client;
import pl.marwik.bank.model.Tag;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private String description;
    @Enumerated(EnumType.STRING)
    private Tag tag;
    @Enumerated(EnumType.STRING)
    private Client client;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private String title;

    public Transaction() {
    }

    public Transaction(Long id, LocalDateTime actionTime, Account from, Account to, BigDecimal amount, String description, Tag tag, Client client) {
        this.id = id;
        this.actionTime = actionTime;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.description = description;
        this.tag = tag;
        this.client = client;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(BigDecimal balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
