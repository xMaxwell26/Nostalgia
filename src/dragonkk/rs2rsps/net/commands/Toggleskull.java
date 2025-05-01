package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Toggleskull implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }

        if (p.Skullon) {
            p.Skullon = false;
            p.getFrames().sendChatMessage(0, "You removed your skull.");
            p.getMask().setApperanceUpdate(true);
        } else {
            p.Skullon = true;
            p.getFrames().sendChatMessage(0, "You put your skull back.");
            p.getMask().setApperanceUpdate(true);
        }
    }

}