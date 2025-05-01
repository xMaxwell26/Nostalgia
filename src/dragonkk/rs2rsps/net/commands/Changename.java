package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Changename implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.canChangename) {
            p.getFrames().sendChatMessage(0, "You can't do this command.");
            return;
        }
        if (args[1].contains("<euro>") || args[1].contains("maxwell")) {
            p.getFrames().sendChatMessage(0, "You can't have that as your username or contain it.");
            return;
        }
        p.setDisplayName(args[1]);
        p.hasChangedname = true;
        p.getMask().setApperanceUpdate(true);
        p.getFrames().sendChatMessage(0, "Your name has been changed to; " + args[1]);
    }

}
