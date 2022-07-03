package site.alex.konon.sol.telegramBot.services;

import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import site.alex.konon.sol.telegramBot.entity.City;

public interface ImageFileService {

    String saveImageInS3(City city, MultipartFile file);

    InputFile getInputFile(City city);
}