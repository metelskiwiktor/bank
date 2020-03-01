package pl.marwik.bank.mapper;

import org.springframework.security.access.method.P;
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

    public static pl.marwik.bank.model.response.account.UserDTO map(User user){
        pl.marwik.bank.model.response.account.UserDTO userDTO = new pl.marwik.bank.model.response.account.UserDTO();
        userDTO.setAccountOwner(user.isAccountOwner());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setGender(user.getGender());
        userDTO.setIdCard(IdCardMapper.map(user.getIdCard()));
        userDTO.setLastName(user.getLastName());
        userDTO.setLogin(user.getLogin());
        userDTO.setPesel(user.getPesel());

        return userDTO;
    }
}
