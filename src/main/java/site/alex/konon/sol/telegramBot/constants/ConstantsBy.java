package site.alex.konon.sol.telegramBot.constants;

public class ConstantsBy extends ConstantsLocalization{
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
                + "Вы зарэгістраваліся для кіравання базай дадзеных Телеграм бота [[bot]]<br>"
                + "Дзякуй";
    }

    @Override
    public String getRegistrationSubjectText() {
        return "Регістрацыя на ";
    }
}
