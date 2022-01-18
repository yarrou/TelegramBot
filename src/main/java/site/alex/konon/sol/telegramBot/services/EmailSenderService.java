package site.alex.konon.sol.telegramBot.services;

import site.alex.konon.sol.telegramBot.entity.User;

public interface EmailSenderService {
    public void sendConfirmRegistrationEmail(User user, String locale);
    public void sendSuccessRegistrationEmail(User user,String locale);
}
