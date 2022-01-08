package site.alex.konon.sol.telegramBot.mapper;

import site.alex.konon.sol.telegramBot.dao.UserFromRequest;
import site.alex.konon.sol.telegramBot.entity.User;

public interface UserMapper {
    User userFromRequestToUser(UserFromRequest dao);
}
