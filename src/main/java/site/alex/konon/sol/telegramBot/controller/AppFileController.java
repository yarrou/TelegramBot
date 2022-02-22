package site.alex.konon.sol.telegramBot.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.alex.konon.sol.telegramBot.constants.Endpoints;
import site.alex.konon.sol.telegramBot.services.AppFileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
public class AppFileController {
    private final AppFileService appFileService;

    public AppFileController(AppFileService appFileService) {
        this.appFileService = appFileService;
    }
    @GetMapping(Endpoints.IS_UPDATE)
    public ResponseEntity isUpdate(@RequestParam(value = "version") String remoteVersion){
        return appFileService.isNewVersion(remoteVersion)?new ResponseEntity(appFileService.getVersionValue(), HttpStatus.OK):
                new ResponseEntity("no updates found",HttpStatus.NOT_FOUND);
    }
    @GetMapping(Endpoints.DOWNLOAD_APP)
    public ResponseEntity downloadApp() throws FileNotFoundException {
        File app = appFileService.getAppFile();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(app));
        return ResponseEntity.ok()
                .contentLength(app.length())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + app.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
