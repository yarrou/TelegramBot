package site.alex.konon.sol.telegramBot.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.alex.konon.sol.telegramBot.entity.City;
import site.alex.konon.sol.telegramBot.repository.CityRepository;
import site.alex.konon.sol.telegramBot.services.CityService;
import site.alex.konon.sol.telegramBot.services.ImageFileService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository repository;
    private final ImageFileService service;

    public CityServiceImpl(CityRepository repository, ImageFileService service) {
        this.repository = repository;
        this.service = service;
    }

    @Override
    public List<String> findCities(String name) {
        List<City> cities = repository.findByNameStartingWith(name);
        List<String> names = cities.stream().map(City::getName).collect(Collectors.toList());
        return names;
    }

    @Override
    public City getCityByName(String name) {
        City city = repository.findByName(name);
        return city;
    }

    @Deprecated
    public List<City> findCity(String name) {
        return repository.findByNameStartingWith(name);
    }

    @Override
    public boolean addCity(City city) {
        if (!repository.existsByName(city.getName())) {
            city.setDateCreated(new Timestamp(new Date().getTime()));
            city.setDateLastModification(new Timestamp(0));
            repository.save(city);
            return true;
        } else return false;
    }

    @Override
    public boolean changeCity(City city) {
        City town = repository.findByName(city.getName());
        if (town != null) {
            town.setText(city.getText());
            town.setDateLastModification(new Timestamp(new Date().getTime()));
            repository.save(town);
            return true;
        } else return false;
    }

    @Override
    public boolean deleteCity(String name) {
        City town = repository.findByName(name);
        if (town != null) {
            repository.delete(town);
            return true;
        } else return false;
    }

    @Override
    public void saveCity(City city, MultipartFile file) {
        City town = repository.findByName(city.getName());
        String link = service.saveImageInS3(city, file);
        if (town != null) {
            town.setText(city.getText());
            town.setDateLastModification(new Timestamp(new Date().getTime()));
            town.setPicture(link);
            repository.save(town);
        } else {
            city.setDateCreated(new Timestamp(new Date().getTime()));
            city.setDateLastModification(new Timestamp(0));
            city.setPicture(link);
            repository.save(city);
        }

    }

    @Override
    public boolean isExist(City city) {
        return repository.existsByName(city.getName());
    }
}
