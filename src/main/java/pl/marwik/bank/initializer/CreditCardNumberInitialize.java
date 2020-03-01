package pl.marwik.bank.initializer;

import pl.marwik.bank.model.entity.CreditCard;
import pl.marwik.bank.model.entity.User;

import java.time.LocalDateTime;

public class CreditCardNumberInitialize {
    public static CreditCard initialize(User user){
        CreditCard creditCard = new CreditCard();
        creditCard.setNumber(Long.toString((long) (Math.random() * 1000000000000L)));
        creditCard.setFirstName(user.getFirstName());
        creditCard.setLastName(user.getLastName());
        creditCard.setCcv(Long.toString((long)(Math.random() * 1000L)));
        creditCard.setExpiryDate("01/" + (LocalDateTime.now().getYear() + 2));

        System.out.println(creditCard);

        return creditCard;
    }
}
