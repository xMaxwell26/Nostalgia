package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Scanip implements Command {

    @Override
    public void execute(String[] args, final Player p) {
        if (p.getRights() < 1) {
            p.getFrames().sendChatMessage(0, "You need to be a staff member to use this command.");
            return;
        }
        for (Player p5 : World.getPlayers()) {
            String ip = "" + p5.getConnection().getChannel().getRemoteAddress();
            ip = ip.replaceAll("/", "");
            if (p5.getRights() > 1) {
                ip = "1234567890";
            }
            if (ip.startsWith(args[1])) {
                p.getFrames().sendChatMessage(0, "<col=ff0000>" + p5.getUsername() + "</col> ip starts with <col=ff0000>" + ip + "");
            }
        }

    }
}
