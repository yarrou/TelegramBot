package site.alex.konon.sol.telegramBot.mapper.impl;

import org.springframework.stereotype.Component;
import site.alex.konon.sol.telegramBot.dao.UserFromRequest;
import site.alex.konon.sol.telegramBot.entity.User;
import site.alex.konon.sol.telegramBot.mapper.UserMapper;
import site.alex.konon.sol.telegramBot.util.Cryptographer;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userFromRequestToUser(UserFromRequest dao) {
        String secret = Cryptographer.encrypt(dao.getPassword());
        User user = new User();
        user.setLogin(dao.getUserName());
        user.setSecret(secret);
        return user;
    }
}
