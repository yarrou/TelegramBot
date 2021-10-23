package site.alex.konon.sol.telegramBot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UtilController {
    @Value("${bot.name}")
    private String botUsername;

    @GetMapping("/name")
    public ResponseEntity findCity() {

        return new ResponseEntity(botUsername, HttpStatus.OK);

    }
}
