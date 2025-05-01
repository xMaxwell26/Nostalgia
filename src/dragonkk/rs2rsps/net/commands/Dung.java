package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Dung implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        p.getFrames().sendInterface(1, 548, 205, 950);
        p.getFrames().sendInterface(1, 746, 93, 950);
        p.spellbook = 3;
    }


}
