package pl.marwik.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marwik.bank.model.entity.Bank;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findBankByName(String name);
}
