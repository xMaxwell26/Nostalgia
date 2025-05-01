package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Reset implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        for (int i = 0; i < 7; i++) {
            p.getSkills().set(i, 1);
            p.getSkills().setXp(i, 0);
            p.getSkills().set(3, 10);
            p.getSkills().setXp(3, 1154);
        }

    }

}
