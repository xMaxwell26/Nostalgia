package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Imsure implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        if (p.sure) {
            p.getFrames().sendChatMessage(0, "You are already sure.");
        } else {
            p.sure = true;
            p.getFrames().sendChatMessage(0, "You are now sure.");
        }

    }
}
