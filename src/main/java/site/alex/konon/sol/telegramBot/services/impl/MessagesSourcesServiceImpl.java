package site.alex.konon.sol.telegramBot.services.impl;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import site.alex.konon.sol.telegramBot.services.MessagesSourcesService;

import java.util.Locale;

@Service
public class MessagesSourcesServiceImpl implements MessagesSourcesService {

    private MessageSource messageSource;

    public MessagesSourcesServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public String getStringValue(String value) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(value, null, locale);
    }
    public String getStringValue(String value,String lang){
        Locale locale = getLocaleFromString(lang);
        return messageSource.getMessage(value,null,locale);
    }
    Locale getLocaleFromString(String lang){
        switch (lang){
            case ("ru"):
                return new Locale("ru");
            case ("by"):
            case ("be"):
                return new Locale("by");
            case ("de"):
                return Locale.GERMAN;
            default: return Locale.ENGLISH;

        }
    }

}
