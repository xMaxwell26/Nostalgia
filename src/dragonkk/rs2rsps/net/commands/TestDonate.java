package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class TestDonate implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        p.getFrames().sendString("test", 411, 0);
        p.getFrames().sendString("test2", 411, 1);
        p.getFrames().sendString("test3", 411, 2);
        p.getFrames().sendString("test4", 411, 3);
        p.getFrames().sendString("test5", 411, 4);
        p.getFrames().sendString("test6", 411, 5);
        p.getFrames().sendInventoryInterface(411);

    }

}