package pl.marwik.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marwik.bank.model.AccountStatus;
import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.entity.CreditCard;
import pl.marwik.bank.model.entity.TransferLimit;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByAccountNumber(String accountNumber);
    Optional<Account> findAccountByCreditCard_NumberAndCreditCard_CcvAndCreditCard_ExpiryDateAndCreditCard_FirstNameAndCreditCard_LastName(String creditCard_number, String creditCard_ccv, String creditCard_expiryDate, String creditCard_firstName, String creditCard_lastName);
    List<Account> findAllByAccountStatus(AccountStatus accountStatus);
}
