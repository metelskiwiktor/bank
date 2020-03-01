package pl.marwik.bank.model.entity;

import pl.marwik.bank.model.entity.Account;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany
    private Set<Account> accounts;
    @Column(unique = true)
    private String branchName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void addAccount(Account account) {
        if (accounts == null) {
            accounts = new LinkedHashSet<>();
        }
        accounts.add(account);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return Objects.equals(accounts, branch.accounts) &&
                Objects.equals(branchName, branch.branchName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accounts, branchName);
    }
}
