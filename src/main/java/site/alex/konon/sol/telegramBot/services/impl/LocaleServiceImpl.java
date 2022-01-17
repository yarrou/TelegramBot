package site.alex.konon.sol.telegramBot.services.impl;

import org.springframework.stereotype.Service;
import site.alex.konon.sol.telegramBot.constants.localizationConstants.*;
import site.alex.konon.sol.telegramBot.services.LocaleService;

@Service
public class LocaleServiceImpl implements LocaleService {
    @Override
    public ConstantsLocalization getLocale(String locale) {
        switch (locale){
            case ("ru"):
                return new ConstantsRu();
            case ("de"):
                return new ConstantsDe();
            case ("be"):
                return new ConstantsBy();
            default:
            return new ConstantsEng();
        }
    }
}
