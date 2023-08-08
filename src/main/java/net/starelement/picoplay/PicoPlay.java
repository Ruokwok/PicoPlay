package net.starelement.picoplay;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import net.starelement.picoplay.cmd.PicoPlayCommand;
import net.starelement.picoplay.game.Game;

import java.util.ArrayList;
import java.util.List;

public class PicoPlay extends PluginBase {

    private static PicoPlay pp;

    public static PicoPlay getInstance() {
        return pp;
    }

    private List<Game> games = new ArrayList<>();
    private Profile profile = new Profile();

    @Override
    public void onLoad() {
        pp = this;
        Server.getInstance().getCommandMap().register("PicoPlay", new PicoPlayCommand());
    }

    @Override
    public void onEnable() {

    }

    public void registerGame(Game game) {

    }

    public Profile getProfile() {
        return profile.clone();
    }
}
