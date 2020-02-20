package pl.marwik.bank.initializer;

import pl.marwik.bank.model.entity.Bank;

public class BankInitialize {
    public static Bank initialize(String bankName){
        Bank bank = new Bank();
        bank.setName(bankName);

        return bank;
    }
}
