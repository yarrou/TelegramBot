package site.alex.konon.sol.telegramBot.constants;

public class ConstantsEng extends ConstantsLocalization {
    @Override
    public String getUnknown() {
        return "We don't know about such a city.";
    }

    @Override
    public String getGreeting() {
        return "Welcome.Enter the name of the city or part of the name";
    }

    @Override
    public String getSuggestion() {
        return "Maybe you meant ";
    }

    @Override
    public String getList() {
        return "The following cities were found according to your request : ";
    }

    @Override
    public String getClarify() {
        return "please specify the request.";
    }

    @Override
    public String getRegistrationEmailText() {
        return "Dear user,<br>"
                + "You have registered to manage the Telegram bot database [[bot]]<br>"
                + "Thank you";
    }

    @Override
    public String getRegistrationSubjectText() {
        return "Registration for the bot";
    }
}
