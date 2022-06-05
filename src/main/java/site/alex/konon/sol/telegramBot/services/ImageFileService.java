package site.alex.konon.sol.telegramBot.services;

import site.alex.konon.sol.telegramBot.entity.City;

import java.io.File;

public interface ImageFileService {
    void writeImageFromString(City city);
    String getImageAsString(City city);
    File getImagePath(City city);
}