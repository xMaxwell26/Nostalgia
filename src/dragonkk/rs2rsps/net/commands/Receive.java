package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.forums.DatabaseFunctions;

//import sun.util.calendar.Gregorian;

public class Receive implements Command {

    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        if (Server.voteDisabled) {
            p.getFrames().sendChatMessage(0, "The donation system is currently offline, an admin must re-enable it.");
            p.getFrames().sendChatMessage(0, "Please wait and try again later.");
            return;
        }
        String name = p.getUsername().replace("_", " ").toLowerCase();
        DatabaseFunctions.addDonateItems(p, name);
        p.getFrames().sendChatMessage(0, "If you have not received the items check your bank to see if you received it");
        p.getFrames().sendChatMessage(0, "when you logged in (Different payment methods through paypal take time).");
    }
}