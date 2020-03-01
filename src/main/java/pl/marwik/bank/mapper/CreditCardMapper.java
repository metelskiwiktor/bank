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
}
