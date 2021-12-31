package site.alex.konon.sol.telegramBot.validator;

import site.alex.konon.sol.telegramBot.entity.City;

public class CityValidator {
    public static boolean cityValidate(City city){
        if (city.getName()==null||city.getName().equals("")) return false;
        else return city.getText() != null && !city.getText().equals("");
    }
}
