package net.starelement.picoplay.level;

import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import net.starelement.picoplay.PicoPlay;
import net.starelement.picoplay.i18n.L;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PicoLevel {

    private static List<PicoLevel> list = new ArrayList<>();

    public static void load() {
        File dir = PicoPlay.getInstance().getLevelDir();
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.getName().endsWith(".zip")) {
                try {
                    ZipFile zip = new ZipFile(file);
                    FileHeader fileHeader = zip.getFileHeader("level.yml");
                    Config config = new Config();
                    config.load(zip.getInputStream(fileHeader));
                    list.add(new PicoLevel(config.getString("name"), config.getList("game")));
                    PicoPlay.getInstance().getLogger().info(L.get("pico.console.load.level") + config.getString("name"));
                } catch (Exception e) {
                    Server.getInstance().getLogger().logException(e);
                }
            }
        }
    }

    private String name;
    private List<String> games;

    public PicoLevel(String name, List<String> games) {
        this.name = name;
        this.games = games;
    }

}
