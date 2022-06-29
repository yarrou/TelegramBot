package site.alex.konon.sol.telegramBot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.alex.konon.sol.telegramBot.dao.UserForm;
import site.alex.konon.sol.telegramBot.constants.Endpoints;
import site.alex.konon.sol.telegramBot.entity.User;
import site.alex.konon.sol.telegramBot.services.UserService;


@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(Endpoints.REGISTRATION)
    public ResponseEntity registration(@RequestBody UserForm userForm, @RequestParam(value = "lang", required = false) String lang) {
        log.info("new registration request for user {}", userForm.getUserName());
        if (lang != null) {
            userService.setLocale(lang);
        }
        try {
            return new ResponseEntity(userService.register(userForm).getToken(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(Endpoints.LOGIN)
    public ResponseEntity login(@RequestBody UserForm userForm, @RequestParam(value = "lang", required = false) String lang) {
        User userFromRequest = userService.convertUserForm(userForm);
        User userFromDB = userService.getUserFromRequest(userForm);
        if (userFromDB.getId() == 0) {
            return new ResponseEntity("such a user is not registered", HttpStatus.NOT_FOUND);
        } else if (!userFromRequest.getSecret().equals(userFromDB.getSecret())) {
            return new ResponseEntity("incorrect password", HttpStatus.UNAUTHORIZED);
        } else if (!userFromDB.isConfirm()) {
            return new ResponseEntity("account is not activated", HttpStatus.UNAUTHORIZED);
        } else {
            String token = userService.loginUser(userFromDB);
            return new ResponseEntity(token, HttpStatus.OK);
        }
    }

    @GetMapping(value = Endpoints.CONFIRM_REGISTRATION)
    public ResponseEntity confirmRegistration(@RequestParam("code") String code, @RequestParam(value = "lang", required = false) String lang) {
        if (lang == null) {
            lang = "en";
        }
        userService.setLocale(lang);
        return userService.confirmRegister(code);
    }
}