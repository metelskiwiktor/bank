package pl.marwik.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marwik.bank.model.entity.TransferLimit;

public interface TransferLimitRepository extends JpaRepository<TransferLimit, Long> {
}
