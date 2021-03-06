package pl.marwik.bank.model.entity;

import pl.marwik.bank.model.Client;
import pl.marwik.bank.model.Role;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private Client client;
    private LocalDateTime expiryDate;
    @Column(unique = true)
    private String value;
    @Enumerated(EnumType.STRING)
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(user, token.user) &&
                client == token.client &&
                Objects.equals(expiryDate, token.expiryDate) &&
                Objects.equals(value, token.value) &&
                role == token.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, client, expiryDate, value, role);
    }
}
