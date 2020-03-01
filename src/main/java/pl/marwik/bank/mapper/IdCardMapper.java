package pl.marwik.bank.mapper;

import pl.marwik.bank.model.entity.IdCard;
import pl.marwik.bank.model.request.IdCardDTO;

public abstract class IdCardMapper {
    public static IdCard map(IdCardDTO idCardDTO){
        IdCard idCard = new IdCard();
        idCard.setBirthDate(idCardDTO.getBirthDate());
        idCard.setExpiryDate(idCardDTO.getExpiryDate());
        idCard.setFatherFirstName(idCardDTO.getFatherFirstName());
        idCard.setMotherFirstName(idCardDTO.getMotherFirstName());
        idCard.setNumber(idCardDTO.getNumber());
        return idCard;
    }

    public static IdCard map(pl.marwik.bank.model.request.login.IdCardDTO idCardDTO){
        IdCard idCard = new IdCard();
        idCard.setBirthDate(idCardDTO.getBirthDate());
        idCard.setExpiryDate(idCardDTO.getExpiryDate());
        idCard.setFatherFirstName(idCardDTO.getFatherFirstName());
        idCard.setMotherFirstName(idCardDTO.getMotherFirstName());
        idCard.setNumber(idCardDTO.getNumber());
        return idCard;
    }

    public static pl.marwik.bank.model.response.account.IdCardDTO map(IdCard idCard){
        pl.marwik.bank.model.response.account.IdCardDTO idCardDTO = new pl.marwik.bank.model.response.account.IdCardDTO();
        idCardDTO.setBirthDate(idCard.getBirthDate());
        idCardDTO.setExpiryDate(idCard.getExpiryDate());
        idCardDTO.setFatherFirstName(idCard.getFatherFirstName());
        idCardDTO.setMotherFirstName(idCard.getMotherFirstName());
        idCardDTO.setFirstName(idCard.getFirstName());
        idCardDTO.setLastName(idCard.getLastName());
        idCardDTO.setNumber(idCard.getNumber());
        return idCardDTO;
    }
}
