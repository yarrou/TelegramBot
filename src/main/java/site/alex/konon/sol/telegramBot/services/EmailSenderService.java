package site.alex.konon.sol.telegramBot.services;

import site.alex.konon.sol.telegramBot.entity.User;

public interface EmailSenderService {
    public void sendRegistrationEmail(User user,String locale);
}
