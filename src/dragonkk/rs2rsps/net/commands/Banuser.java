package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;
import dragonkk.rs2rsps.util.Misc;

public class Banuser implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (p.getRights() == 0) {
            p.getFrames().sendChatMessage(0, "You have to be a staff member to use this command.");
            return;
        }
        if (p.getUsername().equals("maxwell")) {
            Player d = Packets.getPlayerByName(args[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.setBanned(true);
            p.getFrames().sendChatMessage(0, "Banned " + args[1] + " :3");
            d.getConnection().getChannel().disconnect();

        } else {
            p.getFrames().sendChatMessage(0, "You must earn this command.");

        }
    }
}