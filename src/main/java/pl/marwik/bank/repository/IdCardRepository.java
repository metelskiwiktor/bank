package pl.marwik.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marwik.bank.model.entity.IdCard;

public interface IdCardRepository extends JpaRepository<IdCard, Long> {
}
