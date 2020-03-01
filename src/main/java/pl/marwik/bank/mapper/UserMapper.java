package pl.marwik.bank.mapper;

import pl.marwik.bank.model.entity.User;
import pl.marwik.bank.model.request.UserDTO;

public abstract class UserMapper {
    public static User map(UserDTO userDTO){
        User user = new User();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setGender(userDTO.getGender());
        user.setPesel(userDTO.getPesel());
        user.setIdCard(IdCardMapper.map(userDTO.getIdCardDTO()));
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());

        return user;
    }
}
