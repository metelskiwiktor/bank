package pl.marwik.bank.model.request.login;

import pl.marwik.bank.model.Client;

public class CredentialsDTO {
    private String login;
    private String password;
    private Client client;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
