package site.alex.konon.sol.telegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.alex.konon.sol.telegramBot.entity.City;


public interface CityRepository extends JpaRepository<City, Long> {
    public boolean existsByName(String name);

    City findByName(String name);
}