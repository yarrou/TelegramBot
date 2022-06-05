package site.alex.konon.sol.telegramBot.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.alex.konon.sol.telegramBot.entity.City;
import site.alex.konon.sol.telegramBot.services.ImageFileService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Service
public class ImageFileServiceImpl implements ImageFileService {

    @Value("${path.images}")
    private String pathImages;

    @Override
    public void writeImageFromString(City city) {
        String base64String = decodeImage(city.getPicture());
        String[] strings = base64String.split(",");
        String extension;
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            default:
                extension = "jpg";
                break;
        }
        String imagePath = pathImages + "/" + city.getName() + "." + extension;
        byte[] decodedBytes = Base64.getDecoder().decode(city.getPicture());
        try {
            FileUtils.writeByteArrayToFile(new File(imagePath), decodedBytes);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getImageAsString(City city) {
        try {
            File cityImageFile;
            String searchFileName = city.getName() + ".*";
            File dirImages = new File(pathImages);
            FileFilter fileFilter = new WildcardFileFilter(searchFileName);
            File[] imagesArray = dirImages.listFiles(fileFilter);
            if (imagesArray.length == 1){
                cityImageFile = imagesArray[0];
            } else throw new FileNotFoundException();
            byte imageData[] = new FileInputStream(cityImageFile).readAllBytes();
            return encodeImage(imageData);
        } catch (FileNotFoundException e) {
            try {
                byte imageData[] = getClass().getClassLoader().getResourceAsStream("static/images/city.png").readAllBytes();
                String imageDataString = encodeImage(imageData);
                city.setPicture(imageDataString);
                return imageDataString;
            } catch (IOException ex) {
                log.error(e.getLocalizedMessage(), e);
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return "";
        }
    }

    private String encodeImage(byte[] imageByteArray) {
        return Base64.getEncoder().encodeToString(imageByteArray);
    }

    private String decodeImage(String imageDataString) {
        return new String(Base64.getDecoder().decode(imageDataString), StandardCharsets.UTF_8);
    }
}
