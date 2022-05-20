package site.alex.konon.sol.telegramBot.services;

import site.alex.konon.sol.telegramBot.entity.City;

import java.util.List;

public interface CityService {
    List<String> findCities(String name);
    City getCityByName(String name);
    List<City> findCity(String name);
    boolean addCity(City city);
    boolean changeCity(City city);
    boolean deleteCity(String name);
}
