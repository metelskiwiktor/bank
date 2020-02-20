package pl.marwik.bank.mapper;

import pl.marwik.bank.initializer.TokenInitialize;
import pl.marwik.bank.model.Client;
import pl.marwik.bank.model.entity.Token;
import pl.marwik.bank.model.entity.User;

public abstract class TokenMapper {
    public static Token map(Client client, User user){
        Token token = new Token();
        token.setClient(client);
        token.setUser(user);
        token.setValue(TokenInitialize.generate());

        return token;
    }
}
