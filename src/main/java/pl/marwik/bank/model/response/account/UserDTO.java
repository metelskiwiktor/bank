package pl.marwik.bank.model.response.account;

import pl.marwik.bank.model.Gender;
import pl.marwik.bank.model.entity.IdCard;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

public class UserDTO {
    private String firstName;
    private String lastName;
    private String pesel;
    private IdCardDTO idCard;
    private Gender gender;
    private String login;
    private boolean accountOwner;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public IdCardDTO getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCardDTO idCard) {
        this.idCard = idCard;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(boolean accountOwner) {
        this.accountOwner = accountOwner;
    }
}
