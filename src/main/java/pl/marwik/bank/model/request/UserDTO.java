package pl.marwik.bank.model.request;

import pl.marwik.bank.model.Gender;

public class UserDTO {
    private String firstName;
    private String lastName;
    private String pesel;
    private String accountNumber;
    private Gender gender;
    private String login;
    private String password;
    private IdCardDTO idCardDTO;

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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public IdCardDTO getIdCardDTO() {
        return idCardDTO;
    }

    public void setIdCardDTO(IdCardDTO idCardDTO) {
        this.idCardDTO = idCardDTO;
    }
}
