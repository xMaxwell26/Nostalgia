package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;
import dragonkk.rs2rsps.util.Misc;

public class Disablevote implements Command {

    @Override
    public void execute(String[] args, final Player p) {
        if (p.getRights() < 1) {
            p.getFrames().sendChatMessage(0, "You need to be a staff member to use this command.");
            return;
        }
        Player d = Packets.getPlayerByName(args[1]);
        if (d.getRights() > 1) {
            p.getFrames().sendChatMessage(0, "You can't do this to that person.");
            return;
        }
        String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
        if (d == null) {
            p.getFrames().sendChatMessage(0, "That player is offline.");
            return;
        }
        d.getFrames().sendChatMessage(0, "You can't vote anymore on this account.");
        p.getFrames().sendChatMessage(0, "You disabled " + args[1] + " from voting.");
        d.votedisabled = 1;
    }

}
