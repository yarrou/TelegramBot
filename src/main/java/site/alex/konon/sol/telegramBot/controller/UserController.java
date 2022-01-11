package site.alex.konon.sol.telegramBot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.alex.konon.sol.telegramBot.dao.UserForm;
import site.alex.konon.sol.telegramBot.entity.User;
import site.alex.konon.sol.telegramBot.services.UserService;


@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody UserForm userForm){
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
}