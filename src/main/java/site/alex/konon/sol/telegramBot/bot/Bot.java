package site.alex.konon.sol.telegramBot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import site.alex.konon.sol.telegramBot.constants.localizationConstants.ConstantsLocalization;
import site.alex.konon.sol.telegramBot.entity.City;
import site.alex.konon.sol.telegramBot.repository.CityRepository;
import site.alex.konon.sol.telegramBot.services.LocaleService;

import java.util.ArrayList;

@Component
public class Bot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    @Value("${bot.name}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;

    @Autowired
    CityRepository repository;
    @Autowired
    LocaleService localeService;

    ConstantsLocalization localization;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        setLocalization(message);
        String nameUser = message.getFrom().getUserName();
        String signatureUser = message.getFrom().toString();
        logger.info("new message from {} : {}",nameUser,signatureUser);
        if (message != null && message.hasText()) {
            String cityName = message.getText();
            logger.info("message is {}",cityName);
            String answer = localization.getUnknown();

            ArrayList<City> cities = repository.findByNameStartingWith(cityName);
            if (cityName.equals("/start")) {
                answer = localization.getGreeting();
            } else if (!cities.isEmpty()) {
                if (cities.size() == 1) {
                    City requiredCity = cities.get(0);
                    if (cityName.equals(requiredCity.getName())) {
                        answer = requiredCity.getText();
                    } else {
                        answer = localization.getSuggestion() + requiredCity.getName() + " : " + requiredCity.getText();
                    }
                } else {
                    StringBuilder builder = new StringBuilder(localization.getList());
                    for (City city : cities) {
                        builder.append(city.getName() + ",");
                    }
                    builder.append(localization.getClarify());
                    answer = builder.toString();
                }
            }

            sendMsg(message, answer);
        }
    }
    private void setLocalization(Message message){
        String locale = message.getFrom().getLanguageCode();
        localization = localeService.getLocale(locale);
    }
}
