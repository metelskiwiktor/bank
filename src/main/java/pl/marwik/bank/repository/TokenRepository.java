package pl.marwik.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marwik.bank.model.Client;
import pl.marwik.bank.model.entity.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findTokenByValue(String value);
    Optional<Token> findTokenByValueAndIpAddress(String value, String ipAddress);
    Optional<Token> findTokenByUser_LoginAndClient(String userLogin, Client client);
}
