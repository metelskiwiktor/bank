package pl.marwik.bank.initializer;

import java.util.UUID;

public class CreditCardNumberInitialize {
    public static String initialize(){
        return UUID.randomUUID().toString();
    }
}
