package site.alex.konon.sol.telegramBot.services;

import site.alex.konon.sol.telegramBot.constants.localizationConstants.ConstantsLocalization;

public interface LocaleService {
    public ConstantsLocalization getLocale(String locale);
}
