package pl.marwik.bank.initializer;

import java.util.UUID;

public class AccountNumberInitialize {
    public static String generate(){
        return UUID.randomUUID().toString();
    }
}
