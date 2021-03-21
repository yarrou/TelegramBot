package site.alex.konon.sol.telegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.alex.konon.sol.telegramBot.entity.City;

import java.util.ArrayList;


public interface CityRepository extends JpaRepository<City, Long> {
    public boolean existsByName(String name);

    City findByName(String name);
    ArrayList<City> findByNameStartingWith(String name);
}