package pl.marwik.bank.mapper;

import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.response.account.AccountDTO;

import java.util.stream.Collectors;

public class AccountMapper {
    public static AccountDTO map(Account account){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setAccountStatus(account.getAccountStatus());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setCreditCard(CreditCardMapper.map(account.getCreditCard()));
        accountDTO.setTransferLimit(account.getTransferLimit());
        accountDTO.setUsers(account.getUsers().stream().map(UserMapper::map).collect(Collectors.toSet()));
        return accountDTO;
    }
}
