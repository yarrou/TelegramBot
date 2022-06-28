package site.alex.konon.sol.telegramBot.services;

import org.springframework.web.multipart.MultipartFile;
import site.alex.konon.sol.telegramBot.entity.City;

import java.util.List;

public interface CityService {
    List<String> findCities(String name);
    City getCityByName(String name);
    boolean addCity(City city);
    boolean changeCity(City city);
    boolean deleteCity(String name);
    void saveCity(City city,MultipartFile file);
    boolean isExist(City city);
}
