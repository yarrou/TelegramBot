package site.alex.konon.sol.telegramBot.services;

import site.alex.konon.sol.telegramBot.dao.UserFromRequest;
import site.alex.konon.sol.telegramBot.entity.User;

public interface UserService {
    User register(UserFromRequest userForm);
    User getUserById(long id);
    boolean isAdmin(User user);
    User save(User user);
    User getUserByLogin(String login);
    User getUserFromRequest(UserFromRequest userForm);
    User convertUserForm(UserFromRequest userForm);
    String loginUser(User user);
}
