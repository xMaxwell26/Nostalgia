package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;
import dragonkk.rs2rsps.util.Misc;

public class Kick implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (p.getRights() == 0 && !p.SolidGold) {
            p.getFrames().sendChatMessage(0, "You have to be a staff member or Solid Gold to use this command.");
            return;
        }
        Player d = Packets.getPlayerByName(args[1]);
        String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
        if (d == null) {
            p.getFrames().sendChatMessage(0, "That player is offline.");
            return;
        }
        if (d.getRights() > 1) {
            p.getFrames().sendChatMessage(0, "You can't do this to that person.");
            return;
        }
        System.out.println("[" + p.getUsername() + "] has kicked: [" + args[1] + "].");
        d.LogoutDelay = 0;
        d.LogoutDelay--;
        p.getFrames().sendChatMessage(0, "You have kicked [" + args[1] + "].");
        World.unRegisterConnection(d.getConnection());

    }

}
