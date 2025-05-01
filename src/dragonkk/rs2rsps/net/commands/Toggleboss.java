package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Toggleboss implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        if (!p.isDonator) {
            p.getFrames().sendChatMessage(0, "You are not a donator so you can't do this command.");
        } else {
        /*if(p.Boss) {
    p.Boss = false;
	p.getAppearence().setNpcType((short) 8399);
	p.getFrames().sendChatMessage(0, "You are now the boss!");
        p.getMask().setApperanceUpdate(true);
		} else {
        p.Boss = true;
	p.getAppearence().setNpcType((short) -1);
	p.getFrames().sendChatMessage(0, "You are no longer a boss.");
        p.getMask().setApperanceUpdate(true);
        	}
	}*/
            p.getFrames().sendChatMessage(0, "Currently removed.");

        }
    }
}