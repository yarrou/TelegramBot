package site.alex.konon.sol.telegramBot.mapper;

import site.alex.konon.sol.telegramBot.dao.UserForm;
import site.alex.konon.sol.telegramBot.entity.User;

public interface UserMapper {
    User userFromRequestToUser(UserForm dao);
}
