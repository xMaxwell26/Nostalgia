package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;

public class Teletome implements Command {

    @Override
    public void execute(String[] args, final Player p) {
        if (p.getRights() < 1) {
            p.getFrames().sendChatMessage(0, "You need to be a staff member to use this command.");
            return;
        }
        final Player to = Packets.getPlayerByName(args[1]);
        if (to.getRights() > 1) {
            p.getFrames().sendChatMessage(0, "You can't do this to that person.");
            return;
        }
        if (to == null) {
            p.getFrames().sendChatMessage(0, "That player is offline.");
            return;
        }
        int x = p.getLocation().getX();
        int y = p.getLocation().getY();
        int z = p.getLocation().getZ();
        to.getMask().getRegion().teleport(x, y, z, 0);
        to.allowed = true;
        p.getFrames().sendChatMessage(0, "You teleport " + args[1] + " to you.");
    }

}
