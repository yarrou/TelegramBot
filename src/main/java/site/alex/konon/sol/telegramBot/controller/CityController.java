package site.alex.konon.sol.telegramBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.alex.konon.sol.telegramBot.Entity.City;
import site.alex.konon.sol.telegramBot.repository.CityRepository;

@RestController
public class CityController {
    @Autowired
    CityRepository repository;


    @PostMapping("/city")
    public ResponseEntity addNewCity(@RequestBody City city){
        if (!repository.existsByName(city.getName())){
            repository.save(city);
            return new ResponseEntity( HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/city")
    public ResponseEntity deleteCity(@RequestBody City city){
        City town = repository.findByName(city.getName());
        if (town!=null){
            repository.delete(town);
            return new ResponseEntity( HttpStatus.OK);
        }else {
            return new ResponseEntity("not found",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/city")
    public ResponseEntity updateCity(@RequestBody City city){
        City town = repository.findByName(city.getName());
        if (town!=null){
            town.setText(city.getText());
            repository.save(town);
            return new ResponseEntity( HttpStatus.OK);
        }else {
            return new ResponseEntity("not found",HttpStatus.NOT_FOUND);
        }
    }


}