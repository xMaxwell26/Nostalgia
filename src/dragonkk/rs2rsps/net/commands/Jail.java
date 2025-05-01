package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;
import dragonkk.rs2rsps.util.Misc;

public class Jail implements Command {

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
        //World.JailName(args[1]);
        Player d = Packets.getPlayerByName(args[1]);
        String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
        if (d.getRights() > 1) {
            p.getFrames().sendChatMessage(0, "You can't do this to that person.");
            return;
        }
        if (d == null) {
            return;
        }
        p.badperson = d.getUsername().replaceAll("_", " ");
        p.getFrames().requestStringInput(5, "Please type a reason for jailing this player");
    }

}
