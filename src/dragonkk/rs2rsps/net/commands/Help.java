package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Help implements Command {


    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        int number = 0;
        for (int i = 0; i < 316; i++) {
            p.getFrames().sendString("", 275, i);
        }
        for (Player p5 : World.getPlayers()) {
            if (p5 == null)
                continue;
            number++;
            String titles = "";
        }
        p.getFrames().sendString("<col=ff0000><shad=000000>Help", 275, 2);
        p.getFrames().sendString("Help", 275, 14);
        p.getFrames().sendString("To get commands up do ::commands", 275, 17);
        p.getFrames().sendString("Remember to do ::save and ::backup.", 275, 16);
        p.getFrames().sendString("If you still need help, ask a player around you.", 275, 18);
        p.getFrames().sendString("Lots of players are located at ::home.", 275, 19);
        p.animate(1350);
        p.getFrames().sendInterface(275);
    }

}
