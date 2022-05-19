package site.alex.konon.sol.telegramBot.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.alex.konon.sol.telegramBot.entity.City;
import site.alex.konon.sol.telegramBot.services.ImageFileService;

import java.io.*;

@Slf4j
@Service
public class ImageFileServiceImpl implements ImageFileService {

    @Value("${path.images}")
    private String pathImages;

    @Override
    public void writeImageFromString(City city) {
        try {
            String imagePath = pathImages + "/" + city.getName();
            File image = new File(imagePath);

            image.setWritable(true);
            image.setReadable(true);
            FileOutputStream file = new FileOutputStream(image);
            file.write(city.getPicture().getBytes());
            file.close();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public String getImageAsString(City city) {
        try {
            String imagePath = pathImages + "/" + city.getName();
            File file = new File(imagePath);
            FileInputStream imageInFile = new FileInputStream(file);
            StringBuilder resultStringBuilder = new StringBuilder();
            try (BufferedReader br
                         = new BufferedReader(new InputStreamReader(imageInFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    resultStringBuilder.append(line).append("\n");
                }
            }
            return resultStringBuilder.toString();
        } catch (FileNotFoundException e) {
            try {
                byte imageData[]  = getClass().getClassLoader().getResourceAsStream("static/images/city.png").readAllBytes();
                String imageDataString = encodeImage(imageData);
                city.setPicture(imageDataString);
                return imageDataString;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return "";
        }
    }
    private String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64String(imageByteArray);
    }

    private byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }
}
