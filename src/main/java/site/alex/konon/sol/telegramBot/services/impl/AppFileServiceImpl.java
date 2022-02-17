package site.alex.konon.sol.telegramBot.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.alex.konon.sol.telegramBot.services.AppFileService;
import site.alex.konon.sol.telegramBot.util.Version;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AppFileServiceImpl implements AppFileService {
    @Value("${path.app}")
    private String pathDir;
    @Override
    public File getAppFile() {
        File app = null;
        try (Stream<Path> walk = Files.walk(Paths.get(pathDir))) {
            List<File> result = walk.map(x -> x.toFile())
                    .filter(f -> f.getName().endsWith(".apk"))
                    .filter(f -> f.getName().startsWith("ControllerForTelegramBot "))
                    .collect(Collectors.toList());
            if (result.size()==0){
                throw new IllegalArgumentException("not found app file");
            }if (result.size()>1){
                throw new IllegalArgumentException("too many files found");
            }app = result.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return app;
    }

    //the file name should be in the format : ControllerForTelegramBot v-X.X.X-debug.apk
    @Override
    public String getVersionValue(){
        File app = getAppFile();
        return app.getName().split("-")[1];
    }
    @Override
    public boolean isNewVersion(String version){
        Version serverVersion = new Version(getVersionValue());
        Version remoteVersion = new Version(version);
        return serverVersion.compareTo(remoteVersion)>0;
    }


}
