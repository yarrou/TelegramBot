package site.alex.konon.sol.telegramBot.constants.localizationConstants;

public class ConstantsRu extends ConstantsLocalization {

    @Override
    public String getUnknown() {
        return "Нам неизвестно о таком городе.";
    }

    @Override
    public String getGreeting() {
        return "Добро пожаловать.Введите название города или часть названия";
    }

    @Override
    public String getSuggestion() {
        return "Возможно вы имели в виду ";
    }

    @Override
    public String getList() {
        return "По вашему запросу найдены следующие города : ";
    }

    @Override
    public String getClarify() {
        return "пожалуйста уточните запрос.";
    }

    @Override
    public String getRegistrationEmailText() {
        return "Уважаемый пользователь,<br>"
                + "Вы подтвердили регистрацию аккаунта для управления базой данных Телеграм бота [[bot]]<br>"
                + "Спасибо";
    }

    @Override
    public String getRegistrationSubjectText() {
        return "Регистрация на ";
    }

    @Override
    public String getConfirmRegistrationEmailText() {
        return "Уважаемый пользователь,<br>"
                + "Вы регистрируете новый аккаунт для управления базой данных Телеграм бота [[bot]]<br>"
                + "Пожалуйста, пройдите по ссылке для подтверждения регистрации<br>"
                +"[[url]] <br>"
                + "Спасибо,<br>"
                + "[[bot]]";
    }

    @Override
    public String getConfirmRegistrationSubjectText() {
        return "Подтвердите регистрацию";
    }

    @Override
    public String getSuccessConfirmRegistrationMessage() {
        return "Вы успешно подтвердили регистрацию";
    }

    @Override
    public String getAlreadyConfirmRegistrationMessage() {
        return "Регистрация уже была подтверждена, повторять это действие не требуется";
    }

    @Override
    public String getBadConfirmRegistrationTokenMessage() {
        return "Неправильный код подтверждения";
    }

    @Override
    public String getSomeoneWrongMessage() {
        return "что то пошло не так...";
    }

}
