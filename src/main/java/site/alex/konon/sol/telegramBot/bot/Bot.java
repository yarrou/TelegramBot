package site.alex.konon.sol.telegramBot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import site.alex.konon.sol.telegramBot.entity.City;
import site.alex.konon.sol.telegramBot.repository.CityRepository;
import site.alex.konon.sol.telegramBot.services.AppFileService;
import site.alex.konon.sol.telegramBot.services.ImageFileService;
import site.alex.konon.sol.telegramBot.services.MessagesSourcesService;

import java.util.ArrayList;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;
    private final ImageFileService imageFileService;
    private final AppFileService appFileService;
    private final CityRepository repository;
    private final MessagesSourcesService messagesSourcesService;

    public Bot(AppFileService appFileService, CityRepository repository, MessagesSourcesService messagesSourcesService, ImageFileService imageFileService) {
        this.appFileService = appFileService;
        this.imageFileService = imageFileService;
        this.repository = repository;
        this.messagesSourcesService = messagesSourcesService;
    }


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
        String nameUser = message.getFrom().getUserName();
        String lang = getUserLang(message);
        String signatureUser = message.getFrom().toString();
        log.info("new message from {} : {}", nameUser, signatureUser);
        if (message.hasText()) {
            String cityName = message.getText();
            log.info("message is {}", cityName);
            String answer = messagesSourcesService.getStringValue(MessagesSourcesService.UNKNOWN_CITY, lang);

            ArrayList<City> cities = repository.findByNameStartingWith(cityName);
            switch (cityName) {
                case ("/start"):
                    answer = messagesSourcesService.getStringValue(MessagesSourcesService.GREETING_CITY, lang);
                    sendMsg(message, answer);
                    break;
                case ("/download_app"):
                    sendUploadingAFile(message);
                    break;
                case ("/info"):
                    answer = messagesSourcesService.getStringValue(MessagesSourcesService.ABOUT_BOT, lang);
                    sendMsg(message, answer);
                    break;
                default:
                    if (!cities.isEmpty()) {
                        if (cities.size() == 1) {
                            City requiredCity = cities.get(0);
                            if (cityName.equals(requiredCity.getName())) {
                                answer = requiredCity.getText();
                                sendImageUploadingAFile(requiredCity, message.getChatId().toString(), answer);
                                break;
                            } else {
                                answer = messagesSourcesService.getStringValue(MessagesSourcesService.SUGGESTION_CITY, lang) + requiredCity.getName() + " : " + requiredCity.getText();
                            }
                        } else {
                            StringBuilder builder = new StringBuilder(messagesSourcesService.getStringValue(MessagesSourcesService.LIST_CITY, lang));
                            for (City city : cities) {
                                builder.append(city.getName()).append(",");
                            }
                            builder.append(messagesSourcesService.getStringValue(MessagesSourcesService.CLARIFY_CITY, lang));
                            answer = builder.toString();
                        }
                    }
                    sendMsg(message, answer);
            }
        }
    }

    private String getUserLang(Message message) {
        return message.getFrom().getLanguageCode();
    }

    public void sendUploadingAFile(Message message) {
        SendDocument sendDockRequest = new SendDocument();
        sendDockRequest.setChatId(message.getChatId().toString());
        SendChatAction sendChatAction = new SendChatAction();
        sendChatAction.setChatId(message.getChatId().toString());
        sendChatAction.setAction(ActionType.UPLOADDOCUMENT);
        sendDockRequest.setDocument(new InputFile(appFileService.getAppFile()));
        try {
            execute(sendChatAction);
            execute(sendDockRequest);
        } catch (TelegramApiException e) {
            log.error("failed to upload the application file", e);
            e.printStackTrace();
        }
    }

    public void sendImageUploadingAFile(City city, String chatId, String text) {
        // Create send method
        SendPhoto sendPhotoRequest = new SendPhoto();
        // Set destination chat id
        sendPhotoRequest.setChatId(chatId);
        // Set the photo file as a new photo (You can also use InputStream with a constructor overload)
        sendPhotoRequest.setPhoto(imageFileService.getInputFile(city));
        sendPhotoRequest.setCaption(text);
        try {
            // Execute the method
            execute(sendPhotoRequest);
        } catch (TelegramApiException e) {
            log.error(e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
    }

}
