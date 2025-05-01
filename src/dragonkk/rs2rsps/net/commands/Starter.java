package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Starter implements Command {

    @Override
    public void execute(String[] args, Player p) {
        String host = p.getConnection().getChannel().getRemoteAddress().toString();
        host = host.substring(1, host.indexOf(':'));
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        if (p.magicPicked == 0 && p.meleePicked == 0 && p.rangedPicked == 0) {
            p.getFrames().sendInterface(993);
            p.getFrames().sendChatMessage(0, "<col=ff0000>If you can't close this please do ::removestarter.");
        } else {
            p.getFrames().sendChatMessage(0, "You already claimed your starter.");
        }
    }
}