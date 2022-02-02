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
import site.alex.konon.sol.telegramBot.entity.City;
import site.alex.konon.sol.telegramBot.repository.CityRepository;
import site.alex.konon.sol.telegramBot.services.MessagesSourcesService;

import java.util.ArrayList;

@Component
public class Bot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    @Value("${bot.name}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;
    private Message message;

    @Autowired
    CityRepository repository;
    @Autowired
    MessagesSourcesService messagesSourcesService;


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
        message = update.getMessage();
        String nameUser = message.getFrom().getUserName();
        String lang = getUserLang(message);
        String signatureUser = message.getFrom().toString();
        logger.info("new message from {} : {}",nameUser,signatureUser);
        if (message != null && message.hasText()) {
            String cityName = message.getText();
            logger.info("message is {}",cityName);
            String answer = messagesSourcesService.getStringValue(MessagesSourcesService.UNKNOWN_CITY,lang);

            ArrayList<City> cities = repository.findByNameStartingWith(cityName);
            if (cityName.equals("/start")) {
                answer = messagesSourcesService.getStringValue(MessagesSourcesService.GREETING_CITY,lang);
            } else if (!cities.isEmpty()) {
                if (cities.size() == 1) {
                    City requiredCity = cities.get(0);
                    if (cityName.equals(requiredCity.getName())) {
                        answer = requiredCity.getText();
                    } else {
                        answer = messagesSourcesService.getStringValue(MessagesSourcesService.SUGGESTION_CITY,lang) + requiredCity.getName() + " : " + requiredCity.getText();
                    }
                } else {
                    StringBuilder builder = new StringBuilder(messagesSourcesService.getStringValue(MessagesSourcesService.LIST_CITY,lang));
                    for (City city : cities) {
                        builder.append(city.getName() + ",");
                    }
                    builder.append(messagesSourcesService.getStringValue(MessagesSourcesService.CLARIFY_CITY,lang));
                    answer = builder.toString();
                }
            }

            sendMsg(message, answer);
        }
    }

    private String getUserLang(Message message){
        return message.getFrom().getLanguageCode();
    }
}
