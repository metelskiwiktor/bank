package pl.marwik.bank.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class IdCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String number;
    private String expiryDate;
    private String firstName;
    private String lastName;
    private String motherFirstName;
    private String fatherFirstName;
    private String birthDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String idCard) {
        this.number = idCard;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
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

    public String getMotherFirstName() {
        return motherFirstName;
    }

    public void setMotherFirstName(String motherFirstName) {
        this.motherFirstName = motherFirstName;
    }

    public String getFatherFirstName() {
        return fatherFirstName;
    }

    public void setFatherFirstName(String fatherFirstName) {
        this.fatherFirstName = fatherFirstName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdCard idCard = (IdCard) o;
        return Objects.equals(number, idCard.number) &&
                Objects.equals(expiryDate, idCard.expiryDate) &&
                Objects.equals(firstName, idCard.firstName) &&
                Objects.equals(lastName, idCard.lastName) &&
                Objects.equals(motherFirstName, idCard.motherFirstName) &&
                Objects.equals(fatherFirstName, idCard.fatherFirstName) &&
                Objects.equals(birthDate, idCard.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, expiryDate, firstName, lastName, motherFirstName, fatherFirstName, birthDate);
    }
}
