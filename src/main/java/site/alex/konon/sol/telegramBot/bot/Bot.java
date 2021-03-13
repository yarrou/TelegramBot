package site.alex.konon.sol.telegramBot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import site.alex.konon.sol.telegramBot.repository.CityRepository;

@Component
public class Bot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;
    @Autowired
    CityRepository repository;

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
        if (message != null && message.hasText()) {
            String city = message.getText();
            String answer = "Нам неизвестно о таком городе";
            if (city.equals("/start")) {
                answer = "Добро пожаловать.Введите точное название города";
            }

            if (repository.existsByName(city)) {
                answer = repository.findByName(city).getText();
            }

            sendMsg(message, answer);
        }
    }

}