package pl.marwik.bank.mapper;

import pl.marwik.bank.model.entity.Account;
import pl.marwik.bank.model.response.DetailsDTO;

public abstract class DetailsMapper {
    public static DetailsDTO map(Account account){
        DetailsDTO detailsDTO = new DetailsDTO();

        detailsDTO.setAccountNumber(account.getAccountNumber());
        detailsDTO.setBalance(account.getBalance());

        return detailsDTO;
    }
}
