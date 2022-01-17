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
                + "Вы зарегистрировались для управления базой данных Телеграм бота [[bot]]<br>"
                + "Спасибо";
    }

    @Override
    public String getRegistrationSubjectText() {
        return "Регистрация на ";
    }

}
