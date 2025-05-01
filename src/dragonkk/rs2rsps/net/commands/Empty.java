package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Empty implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        if (p.sure) {
            p.getInventory().getContainer().clear();
            p.getInventory().refresh();
            p.getFrames().sendChatMessage(0, "Inventory cleared.");
            p.sure = false;
        } else {
            p.getFrames().sendChatMessage(0, "Please type ::imsure to do this command.");
            p.getFrames().sendChatMessage(0, "<col=ff0000>Warning, this will clear all items in your inventory!");
        }
    }
}
