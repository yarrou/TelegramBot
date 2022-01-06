package site.alex.konon.sol.telegramBot.services;

import site.alex.konon.sol.telegramBot.entity.User;

public interface UserService {
    void register(String login, String password);
    User getUserById(long id);
    boolean isAdmin(User user);
    User saveOrUpdate(User user);
    User getUserByLogin(String login);

}
