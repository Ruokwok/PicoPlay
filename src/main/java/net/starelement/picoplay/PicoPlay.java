package net.starelement.picoplay;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.Utils;
import net.starelement.picoplay.cmd.PicoPlayCommand;
import net.starelement.picoplay.game.Game;
import net.starelement.picoplay.game.GameTemplate;
import net.starelement.picoplay.i18n.L;
import net.starelement.picoplay.level.PicoLevel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
}
