package site.alex.konon.sol.telegramBot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class UtilController {
    @Value("${bot.name}")
    private String botUsername;

    private static final Logger logger = LoggerFactory.getLogger(UtilController.class);

    @GetMapping("/name")
    public ResponseEntity findCity(HttpServletRequest request) {
        logger.info("new util connect from ip {}",request.getRemoteAddr());
        return new ResponseEntity(botUsername, HttpStatus.OK);

    }
}
