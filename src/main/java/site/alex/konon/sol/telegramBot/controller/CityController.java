package site.alex.konon.sol.telegramBot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.alex.konon.sol.telegramBot.entity.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.alex.konon.sol.telegramBot.services.impl.CityServiceImpl;
import site.alex.konon.sol.telegramBot.validator.CityValidator;
import site.alex.konon.sol.telegramBot.validator.TextValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    private final CityServiceImpl cityService;


    public CityController(final CityServiceImpl cityService) {
        this.cityService = cityService;
    }


    @PostMapping("/city")
    public ResponseEntity addNewCity(@RequestBody City city, HttpServletRequest request) {
        logger.info("new post connect from ip {} , and city name is {}",request.getRemoteAddr(),city.getName());
        if(!CityValidator.cityValidate(city)){
            return new ResponseEntity("not valid",HttpStatus.BAD_REQUEST);
        }
        if (cityService.addCity(city)) {
            return new ResponseEntity("added",HttpStatus.OK);
        } else {
            return new ResponseEntity("already exists",HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/city")
    public ResponseEntity deleteCity(@RequestParam(value = "city") String name,HttpServletRequest request) {
        logger.info("new del connect from ip {} , and city name is {}",request.getRemoteAddr(),name);
        if (!TextValidator.noEmptyValidate(name)){
            return new ResponseEntity("not valid",HttpStatus.BAD_REQUEST);
        }
        if(cityService.deleteCity(name)){
            return new ResponseEntity("deleted",HttpStatus.OK);
        } else {
            return new ResponseEntity("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/city")
    public ResponseEntity updateCity(@RequestBody City city,HttpServletRequest request) {
        logger.info("new put connect from ip {} , and city name is {}",request.getRemoteAddr(),city.getName());
        if (!CityValidator.cityValidate(city)){
            return new ResponseEntity("not valid",HttpStatus.BAD_REQUEST);
        }
        if (cityService.changeCity(city)) {
            return new ResponseEntity("changed",HttpStatus.OK);
        } else {
            return new ResponseEntity("not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/city")
    public ResponseEntity findCity(@RequestParam(value = "city") String name, HttpServletRequest request) {
        logger.info("new get connect from ip {} , and city name is {}",request.getRemoteAddr(),name);
        if(!TextValidator.noEmptyValidate(name)){
            return new ResponseEntity("not valid",HttpStatus.BAD_REQUEST);
        }
        City city = cityService.getCityByName(name);
        if (city != null) {
            return new ResponseEntity(city.getText(), HttpStatus.OK);
        } else {
            return new ResponseEntity("not found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    public ResponseEntity search(@RequestParam(value = "city") String name, HttpServletRequest request) {
        logger.info("new find connect from ip {} , and city name is {}",request.getRemoteAddr(),name);
        List<City> cities = cityService.findCity(name);
        if (cities.size()>0){
            return new ResponseEntity(cities,HttpStatus.OK);
        }else return new ResponseEntity(cities,HttpStatus.NOT_FOUND);
    }

}
