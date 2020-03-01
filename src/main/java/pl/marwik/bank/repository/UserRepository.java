package pl.marwik.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marwik.bank.model.entity.IdCard;
import pl.marwik.bank.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByIdCard_Number(String number);
    Optional<User> getUserByIdCard(IdCard idCard);
    Optional<User> getUserByLogin(String login);
    Optional<User> getUserByLoginAndPassword(String login, String password);
}
