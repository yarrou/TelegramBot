package site.alex.konon.sol.telegramBot.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import site.alex.konon.sol.telegramBot.services.ImageFileService;

@RestController
public class ImageController {
    private final ImageFileService service;

    public ImageController(ImageFileService service) {
        this.service = service;
    }

    @GetMapping(value = "/city/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    ByteArrayResource viewImage(@PathVariable String imageId) {
        byte[] image = service.getImageAsByteArray(imageId);
        return new ByteArrayResource(image);
    }
}
