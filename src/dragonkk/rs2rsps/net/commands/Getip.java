package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;

public class Getip implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (p.getRights() == 0 || p.getUsername().equals("tester_kai")) {
            p.getFrames().sendChatMessage(0, "You have to be a staff member to use this command.");
            return;
        }
        Player d = Packets.getPlayerByName(args[1]);
        if (d.getRights() > 1) {
            p.getFrames().sendChatMessage(0, "You can't do this to that person.");
            return;
        }
        //p.getMask().setLastChatMessage(new ChatMessage(0, 0, args[1]+"'s Ip is: "+d.getConnection().getChannel().getRemoteAddress()));
        //p.getMask().setChatUpdate(true);

        p.getFrames().sendChatMessage(0, args[1] + "'s Ip is: " + d.getConnection().getChannel().getRemoteAddress());
    }
}
