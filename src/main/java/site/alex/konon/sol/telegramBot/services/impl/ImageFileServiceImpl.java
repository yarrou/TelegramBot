package site.alex.konon.sol.telegramBot.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import site.alex.konon.sol.telegramBot.entity.City;
import site.alex.konon.sol.telegramBot.services.ImageFileService;
import site.alex.konon.sol.telegramBot.util.MyRequestContextListener;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class ImageFileServiceImpl implements ImageFileService {
    @Autowired
    AmazonS3 s3Client;

    @Value("${do.space.bucket}")
    private String doSpaceBucket;

    //path to the folder with images of cities, set using the environment variable PATH_IMAGES
    @Value("${path.images}")
    private String pathImages;

    @Deprecated
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


    @Deprecated
    public String getImageAsString(City city) {
        File cityImageFile = getImageFile(city);
        try (FileInputStream inputStream = new FileInputStream(cityImageFile)) {
            byte[] imageData = inputStream.readAllBytes();
            return encodeImage(imageData);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return "";
        }
    }

    @Override
    public File getImageFile(City city) {
        try {
            File cityImageFile;
            String[] array = city.getPicture().split("/");

            String searchFileName = pathImages + "/" + array[array.length - 1];
            cityImageFile = new File(searchFileName);
            if (!cityImageFile.exists()) {
                return cityImageFile;
            } else throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            File cityImageFile = new File(pathImages + "/city.png");
            if (!cityImageFile.exists()) {
                createDefaultCityImageFile(cityImageFile);
            }
            return cityImageFile;
        }
    }

    @Override
    public String saveImage(City city, MultipartFile file) {
        String urlImage = new MyRequestContextListener().getMyUrl() + "/image/";
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = city.getName() + "." + extension;
        String imagePath = pathImages + "/" + fileName;
        try {
            FileUtils.writeByteArrayToFile(new File(imagePath), file.getBytes());
            return urlImage + fileName;
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return urlImage + "city.png";
        }
    }

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
    public InputFile getInputFile(City city){
        String[] array = city.getPicture().split("/");
        String searchFileName = pathImages + "/" + array[array.length - 1];
        InputStream is;
        try {
            is = (InputStream) new URL(city.getPicture()).getContent();
        } catch (IOException e) {
            is = getClass().getClassLoader().getResourceAsStream("static/images/city.png");
        }
        return new InputFile(is,searchFileName);
    }

    @Override
    public byte[] getImageAsByteArray(String imageName) {
        try (FileInputStream inputStream = new FileInputStream(findByFileName(imageName))) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new byte[0];
        }

    }

    //a method that creates a default city image.
    private void createDefaultCityImageFile(File file) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/images/city.png")) {
            if (inputStream != null) {
                byte[] imageData = inputStream.readAllBytes();
                FileUtils.writeByteArrayToFile(file, imageData);
            }
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        }
    }


    //method returning encoded string from image byte array
    private String encodeImage(byte[] imageByteArray) {
        return Base64.getEncoder().encodeToString(imageByteArray);
    }

    //method returning the decoded image string
    private String decodeImage(String imageDataString) {
        return new String(Base64.getDecoder().decode(imageDataString), StandardCharsets.UTF_8);
    }

    private File findByFileName(String fileName) {
        Path path = Paths.get(pathImages);
        try (Stream<Path> pathStream = Files.find(path,
                Integer.MAX_VALUE,
                (p, basicFileAttributes) ->
                        p.getFileName().toString().equalsIgnoreCase(fileName))
        ) {
            List<Path> result = pathStream.collect(Collectors.toList());
            if (result.size() != 1) {
                throw new IOException();
            } else {
                return result.get(0).toFile();
            }
        } catch (IOException e) {
            File cityImageFile = new File(pathImages + "/city.png");
            if (!cityImageFile.exists()) {
                createDefaultCityImageFile(cityImageFile);
            }
            return cityImageFile;
        }
    }
}
