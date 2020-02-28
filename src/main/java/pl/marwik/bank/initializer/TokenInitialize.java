package pl.marwik.bank.initializer;

import pl.marwik.bank.model.Client;
import pl.marwik.bank.model.Role;
import pl.marwik.bank.model.entity.Token;
import pl.marwik.bank.model.entity.User;

import java.util.UUID;

public class TokenInitialize {
    public static Token initializeRandomTokenForUserRole(Client client, User user, String ipAddress){
        String tokenValue = UUID.randomUUID().toString();
        Role role = Role.USER;

        Token token = new Token();
        token.setClient(client);
        token.setUser(user);
        token.setValue(tokenValue);
        token.setRole(role);
        token.setIpAddress(ipAddress);
        return token;
    }
}
