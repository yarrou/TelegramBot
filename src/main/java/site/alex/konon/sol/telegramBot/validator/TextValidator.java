package site.alex.konon.sol.telegramBot.validator;

public class TextValidator {
    public static boolean noEmptyValidate(String text) {
        if (text == null) {
            return false;
        } else return !text.trim().equals("");
    }
}
