package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;

public class Teleto implements Command {

    @Override
    public void execute(String[] args, final Player p) {
        if (p.getRights() < 1 && !p.SolidGold) {
            p.getFrames().sendChatMessage(0, "You need to be a staff member to use this command.");
            return;
        }
        final Player to = Packets.getPlayerByName(args[1]);
        if (to == null) {
            p.getFrames().sendChatMessage(0, "That player is offline.");
            return;
        }
        final int x = to.getLocation().getX();
        final int y = to.getLocation().getY();
        p.getMask().getRegion().teleport(x, y, 0, 0);
        p.getFrames().sendChatMessage(0, "You have teleported to " + args[1] + ".");
    }

}
