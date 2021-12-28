package site.alex.konon.sol.telegramBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.alex.konon.sol.telegramBot.entity.City;
import site.alex.konon.sol.telegramBot.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    CityRepository repository;


    @PostMapping("/city")
    public ResponseEntity addNewCity(@RequestBody City city, HttpServletRequest request) {
        logger.info("new post connect from ip {} , and city name is {}",request.getRemoteAddr(),city.getName());
        if (!repository.existsByName(city.getName())) {
            repository.save(city);
            return new ResponseEntity("added",HttpStatus.OK);
        } else {
            return new ResponseEntity("already exists",HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/city")
    public ResponseEntity deleteCity(@RequestParam(value = "city") String name,HttpServletRequest request) {
        logger.info("new del connect from ip {} , and city name is {}",request.getRemoteAddr(),name);
        City town = repository.findByName(name);
        if (town != null) {
            repository.delete(town);
            return new ResponseEntity("deleted",HttpStatus.OK);
        } else {
            return new ResponseEntity("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/city")
    public ResponseEntity updateCity(@RequestBody City city,HttpServletRequest request) {
        logger.info("new put connect from ip {} , and city name is {}",request.getRemoteAddr(),city.getName());
        City town = repository.findByName(city.getName());
        if (town != null) {
            town.setText(city.getText());
            repository.save(town);
            return new ResponseEntity("changed",HttpStatus.OK);
        } else {
            return new ResponseEntity("not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/city")
    public ResponseEntity findCity(@RequestParam(value = "city") String name, HttpServletRequest request) {
        logger.info("new get connect from ip {} , and city name is {}",request.getRemoteAddr(),name);
        City city = repository.findByName(name);
        if (city != null) {
            return new ResponseEntity(city.getText(), HttpStatus.OK);
        } else {
            return new ResponseEntity("not found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    public ResponseEntity find(@RequestParam(value = "city") String name, HttpServletRequest request) {
        logger.info("new find connect from ip {} , and city name is {}",request.getRemoteAddr(),name);
        List<City> cities = repository.findByNameStartingWith(name);
        if (cities.size()>0){
            return new ResponseEntity(cities,HttpStatus.OK);
        }else return new ResponseEntity(cities,HttpStatus.NOT_FOUND);
    }

}
