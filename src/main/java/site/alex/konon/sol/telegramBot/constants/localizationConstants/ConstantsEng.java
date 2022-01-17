package site.alex.konon.sol.telegramBot.constants.localizationConstants;

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
                + "You have confirmed the registration of an account to manage the Telegram bot database [[bot]]<br>"
                + "Thank you";
    }

    @Override
    public String getRegistrationSubjectText() {
        return "Registration for the bot";
    }

    @Override
    public String getConfirmRegistrationEmailText() {
        return "Dear user,<br>"
                + "You are registering a new account for database management Telegram bot [[bot]]<br>"
                + "Please click on the link to confirm your registration<br>"
                + "[[url]] <br>"
                + "Thank you,<br>"
                + "[[bot]]";
    }

    @Override
    public String getConfirmRegistrationSubjectText() {
        return "Confirm registration";
    }

    @Override
    public String getSuccessConfirmRegistrationMessage() {
        return "You have successfully confirmed the registration.";
    }

    @Override
    public String getAlreadyConfirmRegistrationMessage() {
        return "Registration has already been confirmed, there is no need to repeat this action";
    }

    @Override
    public String getBadConfirmRegistrationTokenMessage() {
        return "Incorrect confirmation code";
    }

    @Override
    public String getSomeoneWrongMessage() {
        return "something went wrong...";
    }
}
