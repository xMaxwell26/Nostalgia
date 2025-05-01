package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;
import dragonkk.rs2rsps.util.Misc;

public class Unjail implements Command {

    @Override
    public void execute(String[] args, final Player p) {
        if (p.getRights() < 1) {
            p.getFrames().sendChatMessage(0, "You need to be a staff member to use this command.");
            return;
        }
        /*if(p.getUsername().equals("conrad")) {
			p.getFrames().sendChatMessage(0, "You can't use this command.");
			return;
		}*/
        Player d = Packets.getPlayerByName(args[1]);
        if (d.Jailed == false) {
            p.getFrames().sendChatMessage(0, "You can't unjail this person as they are not in jail.");
            p.getFrames().sendChatMessage(0, "Use the ::sendhome command instead.");
            return;
        }
        if (d.getRights() > 1) {
            p.getFrames().sendChatMessage(0, "You can't do this to that person.");
            return;
        }
        String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
        if (d == null) {
            p.getFrames().sendChatMessage(0, "This player is offline.");
            return;
        }
        d.Jailed = false;
        d.getFrames().sendChatMessage(0, "You have been unjailed by " + myName + ".");
        d.getMask().getRegion().teleport(3186, 3439, 0, 0);
        p.getFrames().sendChatMessage(0, "You have unjailed " + d.getUsername() + ".");
    }

}
