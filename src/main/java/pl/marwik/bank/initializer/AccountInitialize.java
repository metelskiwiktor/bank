package pl.marwik.bank.initializer;

import pl.marwik.bank.model.AccountStatus;
import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.entity.User;

import java.math.BigDecimal;

public class AccountInitialize {
    public static Account initializeAccountInCreateStatus(User user){
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        account.setAccountNumber(AccountNumberInitialize.generate());
        account.setAccountStatus(AccountStatus.CREATING);
        account.setCreditCard(CreditCardNumberInitialize.initialize(user));
        account.setTransferLimit(LimitInitializer.initialize());
        return account;
    }
}
