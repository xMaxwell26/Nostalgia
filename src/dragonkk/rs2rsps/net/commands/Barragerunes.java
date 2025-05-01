package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Barragerunes implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        p.getInventory().addItem(555, 13337);
        p.getInventory().addItem(560, 13337);
        p.getInventory().addItem(565, 13337);
        p.getFrames().sendChatMessage(0, "You spawn some barrage runes.");
    }

}
