package pl.marwik.bank.mapper;

import pl.marwik.bank.model.entity.CreditCard;
import pl.marwik.bank.model.request.login.CreditCardDTO;

public abstract class CreditCardMapper {
    public static CreditCard map(CreditCardDTO creditCardDTO){
        CreditCard creditCard = new CreditCard();
        creditCard.setNumber(creditCardDTO.getNumber());
        creditCard.setLastName(creditCardDTO.getLastName());
        creditCard.setExpiryDate(creditCardDTO.getExpiryDate());
        creditCard.setCcv(creditCardDTO.getCcv());
        creditCard.setFirstName(creditCardDTO.getFirstName());
        return creditCard;
    }

    public static pl.marwik.bank.model.response.account.CreditCardDTO map(CreditCard creditCard){
        pl.marwik.bank.model.response.account.CreditCardDTO creditCardDTO = new pl.marwik.bank.model.response.account.CreditCardDTO();
        creditCardDTO.setCcv(creditCard.getCcv());
        creditCardDTO.setExpiryDate(creditCard.getExpiryDate());
        creditCardDTO.setFirstName(creditCard.getFirstName());
        creditCardDTO.setLastName(creditCard.getLastName());
        creditCardDTO.setNumber(creditCard.getNumber());
        return creditCardDTO;
    }
}
