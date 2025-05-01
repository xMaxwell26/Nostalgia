package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Gfx implements Command {

    @Override
    public void execute(String[] args, final Player p) {
        if (p.getRights() < 1 && !p.SolidGold) {
            p.getFrames().sendChatMessage(0, "You need to be a staff member to use this command.");
            return;
        }
        p.graphics2(Integer.parseInt(args[1]));
        p.getFrames().sendChatMessage(0, "GFX: " + args[1] + ".");
    }

}
