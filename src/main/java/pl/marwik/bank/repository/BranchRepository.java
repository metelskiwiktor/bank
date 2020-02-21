package pl.marwik.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.entity.Branch;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findBranchByAccountsContains(Account account);
    Optional<Branch> findBranchByBranchName(String branchName);
}
