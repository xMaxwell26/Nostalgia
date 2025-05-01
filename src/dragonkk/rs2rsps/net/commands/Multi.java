package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Multi implements Command {

    @Override
    public void execute(String[] args, final Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        if (p.getSkills().playerDead)
            return;
        p.getFrames().sendChatMessage(0, "Please do ::tele to open up the tele menu or click the world map.");
    }

}
