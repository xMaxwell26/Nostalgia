package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Togglepray implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }

        if (p.getPrayer().ancientcurses) {
            p.getPrayer().closeAllPrayers();
            p.getPrayer().switchPrayBook(Boolean.parseBoolean(String.valueOf(false)));
            p.getFrames().sendChatMessage(0, "Prayer book set to normal.");
        } else {
            p.getPrayer().closeAllPrayers();
            p.getPrayer().switchPrayBook(Boolean.parseBoolean(String.valueOf(true)));
            p.getFrames().sendChatMessage(0, "Prayer book set to curses.");
        }
    }

}
