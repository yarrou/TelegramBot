package site.alex.konon.sol.telegramBot.services;

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

    public static final String MESSAGE_SUCCESS_CONFIRM_REGISTRATION = "message.success.confirm.registration";
    public static final String MESSAGE_ALREADY_CONFIRM_REGISTRATION = "message.already.confirm.registration";
    public static final String MESSAGE_BAD_CONFIRM_REGISTRATION_TOKEN = "message.bad.confirm.registration.token";
    public static final String MESSAGE_ALREADY_EXISTS = "message.already.exists";
    public static final String MESSAGE_SOMEONE_WRONG = "message.someone.wrong";
    public static final String MESSAGE_USER_ALREADY_EXISTS = "message.user.already.exists";
    public String getStringValue(String value);
    public String getStringValue(String value,String lang);
}
