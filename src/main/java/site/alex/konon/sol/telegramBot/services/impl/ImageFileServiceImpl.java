package site.alex.konon.sol.telegramBot.services.impl;

import lombok.extern.slf4j.Slf4j;
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
    public void writeImage( City city) {
        try {
            String imagePath = pathImages + "/" + city.getName();
            File image = new File(imagePath);
            image.setWritable(true);
            image.setReadable(true);
            FileOutputStream file = new FileOutputStream(image);
            file.write(city.getPicture().getBytes());
            file.close();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(),e);
        }
    }

    @Override
    public String getImage(City city) {
        try {
            String imagePath = pathImages + "/" + city.getName();
            File file = new File(imagePath);
            if(!file.exists()) file = new File("static/images/city.png");
            String result = null;
            DataInputStream reader = new DataInputStream(new FileInputStream(file));
            int nBytesToRead = reader.available();
            if(nBytesToRead > 0) {
                byte[] bytes = new byte[nBytesToRead];
                reader.read(bytes);
                result = new String(bytes);
            }
            return result;
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(),e);
            return "";
        }
    }
}
