package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Tea implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        int teaAmount = p.getInventory().getFreeSlots();
        p.getInventory().addItem(712, teaAmount);
        p.getFrames().sendChatMessage(0, "You spawn a full inventory of Tea.");

    }
}