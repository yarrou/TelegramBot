package site.alex.konon.sol.telegramBot.services;

import org.springframework.web.multipart.MultipartFile;
import site.alex.konon.sol.telegramBot.entity.City;

import java.io.File;

public interface ImageFileService {
    File getImageFile(City city);

    String saveImage(City city, MultipartFile file);

    byte[] getImageAsByteArray(String imageName);
}