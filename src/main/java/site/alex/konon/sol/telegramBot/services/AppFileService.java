package site.alex.konon.sol.telegramBot.services;

import java.io.File;

public interface AppFileService {
    File getAppFile();
    boolean isNewVersion(String version);
    String getVersionValue();
}
