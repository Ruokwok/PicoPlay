package net.starelement.picoplay;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.Utils;
import net.starelement.picoplay.cmd.PicoPlayCommand;
import net.starelement.picoplay.game.Game;
import net.starelement.picoplay.game.GameTemplate;
import net.starelement.picoplay.i18n.L;
import net.starelement.picoplay.level.PicoLevel;
import org.iq80.leveldb.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PicoPlay extends PluginBase {

    private static PicoPlay pp;

    public static PicoPlay getInstance() {
        return pp;
    }

    private List<GameTemplate> games = new ArrayList<>();
    private Profile profile = new Profile();
    private File levelDir;

    @Override
    public void onLoad() {
        pp = this;
        levelDir = new File(getDataFolder() + "/levels");
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        if (!levelDir.exists()) levelDir.mkdir();
        Config config = getConfig();
        if (config.get("version") == null) {
            config.set("version", 1);
            config.set("language", "chs");
            config.save();
        }
        L.load();
        L.setDefaultCode(config.getString("language"));
        Server.getInstance().getCommandMap().register("PicoPlay", new PicoPlayCommand());
    }

    @Override
    public void onEnable() {
        PicoLevel.load();
        clean();
    }

    public void registerGame(Class game) {
        if (Game.class.isAssignableFrom(game)) {
            if (games.contains(game)) return;
            GameTemplate gt = new GameTemplate(game);
            for (PicoLevel level : PicoLevel.getList()) {
                if (level.getGame().equals(game.getSimpleName())) {
                    gt.addLevel(level);
                }
            }
            games.add(gt);
            getLogger().info(L.get("chs", "pico.console.register.game") + game.getSimpleName());
        } else {
            throw new RuntimeException();
        }
    }

    public Profile getProfile() {
        return profile.clone();
    }

    public File getLevelDir() {
        return levelDir;
    }

    public void reset() {
        Collections.shuffle(games);
    }

    public GameTemplate getGameTemplate(int i) {
        return games.get(i);
    }

    public int getGameCount() {
        return games.size();
    }

    public void clean() {
        Map<Integer, Level> levels = Server.getInstance().getLevels();
        for (Map.Entry<Integer, Level> entry : levels.entrySet()) {
            Level level = entry.getValue();
            if (level != Server.getInstance().getDefaultLevel()) {
                level.unload();
                File file = new File("worlds/" + level.getFolderName());
                try {
                    FileUtils.deleteRecursively(file);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        File worlds = new File("worlds");
        for (File file : worlds.listFiles()) {
            if (!Server.getInstance().getDefaultLevel().getFolderName().equals(file.getName())) {
                try {
                    FileUtils.deleteRecursively(file);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void readyGame(GameTemplate gt) {
        PicoLevel pl = gt.getRandomLevel();
    }
}
