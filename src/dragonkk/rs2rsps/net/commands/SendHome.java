package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;

public class SendHome implements Command {

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
        to.getMask().getRegion().teleport(3186, 3440, 0, 0);
        to.allowed = false;
        p.getFrames().sendChatMessage(0, "You sent " + args[1] + " home.");
    }

}
