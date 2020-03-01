package pl.marwik.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marwik.bank.model.entity.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
