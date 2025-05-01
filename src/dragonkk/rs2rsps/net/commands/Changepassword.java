package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Changepassword implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (args[1].contains("<euro>") || args[1].contains("password") || args[1].contains("majda") || args[1].contains("gfkogfko")) {
            p.getFrames().sendChatMessage(0, "You can't have that as your password.");
            return;
        }
        if (args[1].length() == 1) {
            p.getFrames().sendChatMessage(0, "You can't have just 1 letter or number as a password.");
            return;
        }
        if (args[1].length() > 20) {
            p.getFrames().sendChatMessage(0, "Your password can't be over 20 characters.");
            return;
        }
        p.setPassword(args[1]);
        p.getFrames().sendChatMessage(0, "Your password has been changed to; " + args[1]);
    }

}
