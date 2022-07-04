package site.alex.konon.sol.telegramBot.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import site.alex.konon.sol.telegramBot.entity.City;
import site.alex.konon.sol.telegramBot.services.ImageFileService;

import java.io.*;
import java.net.URL;

@Slf4j
@Service
public class ImageFileServiceImpl implements ImageFileService {
    @Autowired
    AmazonS3 s3Client;

    @Value("${do.space.bucket}")
    private String doSpaceBucket;

    @Override
    public String saveImageInS3(City city, MultipartFile file) {
        ObjectMetadata data = new ObjectMetadata();
        data.setContentType(file.getContentType());
        data.setContentLength(file.getSize());
        String filepath = "telegrambot/cities/" + city.getName() + ".jpg";
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(doSpaceBucket,
                    filepath, file.getInputStream(), data).withCannedAcl(CannedAccessControlList.PublicRead);
            s3Client.putObject(putObjectRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return s3Client.getUrl(doSpaceBucket, filepath).toString();
    }

    @Override
    public InputFile getInputFile(City city) {
        String[] array = city.getPicture().split("/");
        String searchFileName = array[array.length - 1];
        InputStream is;
        try {
            is = (InputStream) new URL(city.getPicture()).getContent();
        } catch (IOException e) {
            is = getClass().getClassLoader().getResourceAsStream("static/images/city.png");
        }
        return new InputFile(is, searchFileName);
    }

}
