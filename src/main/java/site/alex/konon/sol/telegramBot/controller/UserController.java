package site.alex.konon.sol.telegramBot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.alex.konon.sol.telegramBot.dao.UserForm;
import site.alex.konon.sol.telegramBot.constants.Endpoints;
import site.alex.konon.sol.telegramBot.entity.User;
import site.alex.konon.sol.telegramBot.services.UserService;
import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
public class UserController {
    private final String confirmRegistration = Endpoints.CONFIRM_REGISTRATION;//
    @Autowired
    private HttpServletRequest request;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(Endpoints.REGISTRATION)
    public ResponseEntity registration(@RequestBody UserForm userForm,@RequestParam(value = "lang",required = false) String lang){
        String locale = lang;
        if(locale!=null){
            userService.setLocale(locale);
        }
        try {
            return new ResponseEntity(userService.register(userForm).getToken(), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(Endpoints.LOGIN)
    public ResponseEntity login(@RequestBody UserForm userForm,@RequestParam(value = "lang",required = false) String lang){
        User userFromRequest = userService.convertUserForm(userForm);
        User userFromDB = userService.getUserFromRequest(userForm);
        if(userFromDB.getId()==0){
            return new ResponseEntity("such a user is not registered",HttpStatus.NOT_FOUND);
        }
        else if (!userFromRequest.getSecret().equals(userFromDB.getSecret())){
            return new ResponseEntity("incorrect password",HttpStatus.UNAUTHORIZED);
        }
        else if(!userFromDB.isConfirm()){
            return new ResponseEntity("account is not activated",HttpStatus.UNAUTHORIZED);
        }
        else {
            String token = userService.loginUser(userFromDB);
            return new ResponseEntity(token,HttpStatus.OK);
        }
    }
    @GetMapping(value = Endpoints.CONFIRM_REGISTRATION)
    public ResponseEntity confirmRegistration(@RequestParam("code") String code,@RequestParam(value = "lang",required = false) String lang ){
        if (lang==null){
            lang="en";
        }
        userService.setLocale(lang);
        return userService.confirmRegister(code);
    }

    @Deprecated
    private String getLanguage() {
        return request.getHeader("Device_used_language");
    }

}