package site.alex.konon.sol.telegramBot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.alex.konon.sol.telegramBot.entity.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.alex.konon.sol.telegramBot.entity.User;
import site.alex.konon.sol.telegramBot.services.CityService;
import site.alex.konon.sol.telegramBot.services.MessagesSourcesService;
import site.alex.konon.sol.telegramBot.services.UserService;
import site.alex.konon.sol.telegramBot.validator.CityValidator;
import site.alex.konon.sol.telegramBot.validator.TextValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);
    private final CityService cityService;
    private final UserService userService;
    private final MessagesSourcesService messagesSourcesService;

    public CityController(final CityService cityService, UserService userService, MessagesSourcesService messagesSourcesService) {
        this.cityService = cityService;
        this.userService = userService;
        this.messagesSourcesService = messagesSourcesService;
    }


    @PostMapping("/city")
    public ResponseEntity addNewCity(@RequestBody City city, HttpServletRequest request,@RequestHeader("auth-token") String token,
                                     @RequestParam(name = "lang",required = false) String lang) {
        logger.info("new post connect from ip {} , and city name is {}",request.getRemoteAddr(),city.getName());
        User user = userService.getUserByToken(token);
        if (user.getId()==0&&!user.isConfirm()){
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_NOT_PRIVILEGES),HttpStatus.UNAUTHORIZED);
        }
        if(!CityValidator.cityValidate(city)){
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_NOT_VALID_DATA),HttpStatus.BAD_REQUEST);
        }
        if (cityService.addCity(city)) {
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_SUCCESSFULLY_ADDED),HttpStatus.OK);
        } else {
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_ALREADY_EXISTS),HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/city")
    public ResponseEntity deleteCity(@RequestParam(value = "city") String name,HttpServletRequest request,@RequestHeader("auth-token") String token,
            @RequestParam(name = "lang",required = false) String lang) {
        logger.info("new del connect from ip {} , and city name is {}",request.getRemoteAddr(),name);
        User user = userService.getUserByToken(token);
        if (user.getId()==0&&!user.isConfirm()){
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_NOT_PRIVILEGES),HttpStatus.UNAUTHORIZED);
        }
        if (!TextValidator.noEmptyValidate(name)){
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_NOT_VALID_DATA),HttpStatus.BAD_REQUEST);
        }
        if(cityService.deleteCity(name)){
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_DATA_DELETED),HttpStatus.OK);
        } else {
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/city")
    public ResponseEntity updateCity(@RequestBody City city,HttpServletRequest request,@RequestHeader("auth-token") String token,
                                     @RequestParam(name = "lang",required = false) String lang) {
        logger.info("new put connect from ip {} , and city name is {}",request.getRemoteAddr(),city.getName());
        User user = userService.getUserByToken(token);
        if (user.getId()==0&&!user.isConfirm()){
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_NOT_PRIVILEGES),HttpStatus.UNAUTHORIZED);
        }
        if (!CityValidator.cityValidate(city)){
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_NOT_VALID_DATA),HttpStatus.BAD_REQUEST);
        }
        if (cityService.changeCity(city)) {
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_DATA_CHANGED),HttpStatus.OK);
        } else {
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/city")
    public ResponseEntity findCity(@RequestParam(value = "city") String name, HttpServletRequest request,
                                   @RequestParam(name = "lang",required = false) String lang) {
        logger.info("new get connect from ip {} , and city name is {}",request.getRemoteAddr(),name);
        if(!TextValidator.noEmptyValidate(name)){
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_NOT_VALID_DATA),HttpStatus.BAD_REQUEST);
        }
        City city = cityService.getCityByName(name);
        if (city != null) {
            return new ResponseEntity(city, HttpStatus.OK);
        } else {
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_NOT_FOUND),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    public ResponseEntity search(@RequestParam(value = "city") String name, HttpServletRequest request,
                                 @RequestParam(name = "lang",required = false) String lang) {
        logger.info("new find connect from ip {} , and city name is {}",request.getRemoteAddr(),name);
        List<String> cities = cityService.findCities(name);
        if (cities.size()>0){
            return new ResponseEntity(cities,HttpStatus.OK);
        }else return new ResponseEntity(cities,HttpStatus.NOT_FOUND);
    }
}
