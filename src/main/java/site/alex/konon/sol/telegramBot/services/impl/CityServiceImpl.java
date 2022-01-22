package site.alex.konon.sol.telegramBot.services.impl;

import org.springframework.stereotype.Service;
import site.alex.konon.sol.telegramBot.entity.City;
import site.alex.konon.sol.telegramBot.repository.CityRepository;
import site.alex.konon.sol.telegramBot.services.CityService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository repository;

    public CityServiceImpl(CityRepository repository) {
        this.repository = repository;
    }

    @Override
    public City getCityByName(String name) {
        return repository.findByName(name);
    }

    @Override
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
}
