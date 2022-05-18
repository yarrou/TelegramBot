package site.alex.konon.sol.telegramBot.services;

import site.alex.konon.sol.telegramBot.entity.City;

public interface ImageFileService {
    void writeImageFromString(City city);
    String getImageAsString(City city);
}