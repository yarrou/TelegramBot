package site.alex.konon.sol.telegramBot.constants.localizationConstants;

public class ConstantsBy extends ConstantsLocalization {
    @Override
    public String getUnknown() {
        return "Нам невядома аб такім горадзе.";
    }

    @Override
    public String getGreeting() {
        return "Калі ласка, увядзіце назву горада або частка назвы";
    }

    @Override
    public String getSuggestion() {
        return "Магчыма вы мелі на ўвазе";
    }

    @Override
    public String getList() {
        return "Па вашаму запыту знойдзены наступныя гарады : ";
    }

    @Override
    public String getClarify() {
        return "калі ласка удакладніце запыт.";
    }
    @Override
    public String getRegistrationEmailText() {
        return "Паважаны карыстальнік,<br>"
                + "Вы пацвердзілі рэгістрацыю акаўнта для кіравання базай дадзеных Телеграм бота [[bot]]<br>"
                + "Дзякуй";
    }

    @Override
    public String getRegistrationSubjectText() {
        return "Регістрацыя на ";
    }

    @Override
    public String getConfirmRegistrationEmailText() {
        return "Паважаны карыстальнік,<br>"
                + "Вы рэгіструеце новы акаўнт для кіравання базай дадзеных Телеграм бота [[bot]]<br>"
                + "Калі ласка, прайдзіце па спасылцы для пацверджання рэгістрацыі<br>"
                +"[[url]] <br>"
                + "Дзякуй,<br>"
                + "[[bot]]";
    }

    @Override
    public String getConfirmRegistrationSubjectText() {
        return "Пацвердзіце рэгістрацыю";
    }

    @Override
    public String getSuccessConfirmRegistrationMessage() {
        return "Вы паспяхова пацвердзілі рэгістрацыю";
    }

    @Override
    public String getAlreadyConfirmRegistrationMessage() {
        return "Рэгістрацыя ўжо была пацверджана, паўтараць гэта дзеянне не патрабуецца";
    }

    @Override
    public String getBadConfirmRegistrationTokenMessage() {
        return "Няправільны код пацверджання";
    }

    @Override
    public String getSomeoneWrongMessage() {
        return "нешта пайшло не так...";
    }
}
