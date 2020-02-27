package pl.marwik.bank.model.helper;

import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.entity.Branch;
import pl.marwik.bank.model.entity.User;

public abstract class Name {
    public static String getName(Account account, Branch branch){
        return String.join(" ", branch.getBranchName(), account.getAccountNumber());
    }
}
