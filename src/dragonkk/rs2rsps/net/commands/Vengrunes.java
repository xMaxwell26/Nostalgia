package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Vengrunes implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        p.getInventory().addItem(9075, 13337);
        p.getInventory().addItem(557, 13337);
        p.getInventory().addItem(560, 13337);
        p.getFrames().sendChatMessage(0, "You spawn some veng runes.");
    }

}
