package net.starelement.picoplay.cmd;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class PicoPlayCommand extends Command {

    public PicoPlayCommand() {
        super("pp", "PicoPlay");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        switch (strings[0]) {
            case "play":
                if (commandSender.isPlayer() && commandSender.isOp()) {
                    Player player = (Player) commandSender;
                    System.out.println();
                }
                break;
        }
        return false;
    }
}
