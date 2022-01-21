package site.alex.konon.sol.telegramBot.services;

import java.util.Locale;

public interface MessagesSourcesService {
    public static final String UNKNOWN_CITY="bot.city.unknown";
    public static final String GREETING_CITY="bot.greeting";
    public static final String SUGGESTION_CITY="bot.city.suggestion";
    public static final String LIST_CITY="bot.city.list";
    public static final String CLARIFY_CITY="bot.city.clarify";

    public static final String EMAIL_REGISTRATION="email.registration";
    public static final String EMAIL_REGISTRATION_SUBJECT="email.registration.subject";
    public static final String EMAIL_CONFIRM_REGISTRATION="email.confirm.registration";
    public static final String EMAIL_CONFIRM_REGISTRATION_SUBJECT="email.confirm.registration.subject";

    public static final String MESSAGE_ALREADY_EXISTS="message.already.exists";
    public String getStringValue(String value);
    public String getStringValue(String value,String lang);
}
