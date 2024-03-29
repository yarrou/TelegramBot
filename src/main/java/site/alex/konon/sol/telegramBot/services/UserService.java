package site.alex.konon.sol.telegramBot.services;

import org.springframework.http.ResponseEntity;
import site.alex.konon.sol.telegramBot.dao.UserForm;
import site.alex.konon.sol.telegramBot.entity.User;

public interface UserService {
    void setLocale(String locale);
    User register(UserForm userForm);
    User getUserById(long id);
    boolean isAdmin(User user);
    User save(User user);
    User getUserByLogin(String login);
    User getUserFromRequest(UserForm userForm);
    User convertUserForm(UserForm userForm);
    String loginUser(User user);
    ResponseEntity confirmRegister(String code);
    User getUserByToken(String token);
}
