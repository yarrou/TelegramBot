package site.alex.konon.sol.telegramBot.services;

import java.util.Locale;

public interface MessagesSourcesService {
    public static final String UNKNOWN_CITY="bot.city.unknown";
    public static final String GREETING_CITY="bot.greeting";
    public static final String SUGGESTION_CITY="bot.city.suggestion";
    public static final String LIST_CITY="bot.city.list";
    public static final String CLARIFY_CITY="bot.city.clarify";
    public String getStringValue(String value);
    public String getStringValue(String value,String lang);
}
