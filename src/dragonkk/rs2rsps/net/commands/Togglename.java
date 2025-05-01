package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Togglename implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }

        if (p.addingFriend) {
            p.addingFriend = false;
            p.getMask().setApperanceUpdate(true);
            //p.getMask().setChatUpdate(true);
            p.getFrames().sendChatMessage(0, "Custom name toggled on.");
        } else {
            p.addingFriend = true;
            p.getMask().setApperanceUpdate(true);
            //p.getMask().setChatUpdate(true);
            p.getFrames().sendChatMessage(0, "Custom name toggled off.");
        }
    }

}
