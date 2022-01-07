package site.alex.konon.sol.telegramBot.services;

public interface TokenService {
    String generateAuthToken(String clientId);
    boolean checkToken(String token);
    String getRandom();
}