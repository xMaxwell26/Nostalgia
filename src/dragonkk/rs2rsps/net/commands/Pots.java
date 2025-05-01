package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Pots implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        p.getInventory().addItem(2437, 100);
        p.getInventory().addItem(2441, 100);
        p.getInventory().addItem(2443, 100);
        p.getInventory().addItem(3025, 100);
        p.getInventory().addItem(2435, 100);
        p.getInventory().addItem(6686, 100);
        p.getInventory().addItem(3041, 100);
        p.getInventory().addItem(2445, 100);
        p.getInventory().addItem(4046, 100);
        p.getFrames().sendChatMessage(0, "You spawn some potions.");

    }
}