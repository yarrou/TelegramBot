package site.alex.konon.sol.telegramBot.services;

import site.alex.konon.sol.telegramBot.entity.City;

public interface ImageFileService {
    void writeImage( City city);
    String getImage(City city);
}