package net.starelement.picoplay;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import net.starelement.picoplay.cmd.PicoPlayCommand;
import net.starelement.picoplay.game.Game;
import net.starelement.picoplay.i18n.L;

import java.util.ArrayList;
import java.util.List;

public class PicoPlay extends PluginBase {

    private static PicoPlay pp;

    public static PicoPlay getInstance() {
        return pp;
    }

    private List<Class> games = new ArrayList<>();
    private Profile profile = new Profile();

    @Override
    public void onLoad() {
        pp = this;
        L.load();
        L.setDefaultCode("chs");
        Server.getInstance().getCommandMap().register("PicoPlay", new PicoPlayCommand());
    }

    @Override
    public void onEnable() {

    }

    public void registerGame(Class game) {
        if (Game.class.isAssignableFrom(game)) {
            if (games.contains(game)) return;
            games.add(game);
            getLogger().info(L.get("chs", "pico.console.register.game") + game.getName());
        } else {
            throw new RuntimeException();
        }
    }

    public Profile getProfile() {
        return profile.clone();
    }
}
