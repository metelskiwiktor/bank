package pl.marwik.bank.initializer;

import pl.marwik.bank.service.helper.RandomDigits;

import java.util.UUID;

public class AccountNumberInitialize {
    public static String generate(){
        System.out.println(RandomDigits.randomDecimalString(24));
        return RandomDigits.randomDecimalString(24);
    }
}
