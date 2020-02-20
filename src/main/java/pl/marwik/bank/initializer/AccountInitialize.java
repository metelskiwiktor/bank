package pl.marwik.bank.initializer;

import pl.marwik.bank.model.entity.Account;

import java.math.BigDecimal;

public class AccountInitialize {
    public static Account generate(){
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        account.setAccountNumber(AccountNumberInitialize.generate());

        return account;
    }
}
