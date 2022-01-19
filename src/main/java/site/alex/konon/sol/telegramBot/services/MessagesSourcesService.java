package site.alex.konon.sol.telegramBot.services;

import java.util.Locale;

public interface MessagesSourcesService {
    public String getStringValue(String value);
    public String getStringValue(String value,String lang);
}
