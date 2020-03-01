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
}
