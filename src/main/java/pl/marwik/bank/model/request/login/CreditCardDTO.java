package pl.marwik.bank.model.request.login;

import pl.marwik.bank.model.Client;

public class CreditCardDTO {
    private String cardNumber;
    private Client client;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
