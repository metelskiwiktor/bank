package pl.marwik.bank.model.request.login;

import pl.marwik.bank.model.Client;

public class IdCardDTO {
    private String idCard;
    private Client client;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
