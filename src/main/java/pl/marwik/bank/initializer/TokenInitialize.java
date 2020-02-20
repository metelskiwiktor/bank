package pl.marwik.bank.initializer;

import java.util.UUID;

public class TokenInitialize {
    public static String generate(){
        return UUID.randomUUID().toString();
    }
}
