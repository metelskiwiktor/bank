package pl.marwik.bank.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String expiryDate;
    @Column(unique = true)
    private String number;
    private String ccv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(expiryDate, that.expiryDate) &&
                Objects.equals(number, that.number) &&
                Objects.equals(ccv, that.ccv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, expiryDate, number, ccv);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", number='" + number + '\'' +
                ", ccv='" + ccv + '\'' +
                '}';
    }
}
