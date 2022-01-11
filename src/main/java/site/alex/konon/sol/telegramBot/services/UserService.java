package site.alex.konon.sol.telegramBot.services;

import site.alex.konon.sol.telegramBot.dao.UserForm;
import site.alex.konon.sol.telegramBot.entity.User;

public interface UserService {
    User register(UserForm userForm);
    User getUserById(long id);
    boolean isAdmin(User user);
    User save(User user);
    User getUserByLogin(String login);
    User getUserFromRequest(UserForm userForm);
    User convertUserForm(UserForm userForm);
    String loginUser(User user);
}
