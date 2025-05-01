package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.OpenCB;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Removestarter implements Command {

    @Override
    public void execute(String[] args, Player p) {
        String host = p.getConnection().getChannel().getRemoteAddress().toString();
        host = host.substring(1, host.indexOf(':'));
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        World.Starterip(host);
        //p.getBank().openBank();
        OpenCB.close(p);
        p.magicPicked = 1;
        p.meleePicked = 1;
        p.rangedPicked = 1;
        p.LastIp = 1;
        p.removeStarter = 1;
        p.getFrames().sendChatMessage(0, "Starter screen removed.");
    }
}