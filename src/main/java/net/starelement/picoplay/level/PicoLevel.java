package net.starelement.picoplay.level;

import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.starelement.picoplay.PicoPlay;
import net.starelement.picoplay.i18n.L;

import java.io.File;
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
                    list.add(new PicoLevel(config.getString("name"), config.getString("game"), zip));
                    PicoPlay.getInstance().getLogger().info(L.get("pico.console.load.level") + config.getString("name"));
                } catch (Exception e) {
                    Server.getInstance().getLogger().logException(e);
                }
            }
        }
    }

    public static List<PicoLevel> getList() {
        return list;
    }

    private String name;
    private String game;
    private ZipFile zip;

    public PicoLevel(String name, String game, ZipFile zip) {
        this.name = name;
        this.game = game;
        this.zip = zip;
    }

    public String getGame() {
        return game;
    }

    public void loadLevel() {
        try {
            zip.extractAll("worlds/" + name);
            Server.getInstance().loadLevel(name);
        } catch (ZipException e) {
            Server.getInstance().getLogger().logException(e);
        }
    }
}
