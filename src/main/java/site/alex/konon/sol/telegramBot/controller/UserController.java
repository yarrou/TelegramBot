package site.alex.konon.sol.telegramBot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.alex.konon.sol.telegramBot.dao.UserForm;
import site.alex.konon.sol.telegramBot.entity.User;
import site.alex.konon.sol.telegramBot.services.UserService;
import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
public class UserController {
    @Autowired
    private HttpServletRequest request;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody UserForm userForm){
        String locale = getLanguage();
        if(locale!=null){
            userService.setLocale(locale);
        }
        try {
            return new ResponseEntity(userService.register(userForm).getToken(), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserForm userForm){
        User userFromRequest = userService.convertUserForm(userForm);
        User userFromDB = userService.getUserFromRequest(userForm);
        if(userFromDB.getId()==0){
            return new ResponseEntity("such a user is not registered",HttpStatus.NOT_FOUND);
        }
        else if (!userFromRequest.getSecret().equals(userFromDB.getSecret())){
            return new ResponseEntity("incorrect password",HttpStatus.UNAUTHORIZED);
        }
        else {
            String token = userService.loginUser(userFromDB);
            return new ResponseEntity(token,HttpStatus.OK);
        }
    }

    private String getLanguage() {
        return request.getHeader("Device_used_language");
    }

}