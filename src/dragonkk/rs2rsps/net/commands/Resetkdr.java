package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Resetkdr implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (p.sure) {
            p.getMask().setLastChatMessage(new ChatMessage(0, 0, "I have reset my Nostalgia KDR!"));
            p.getMask().setChatUpdate(true);
            p.Kills2 = 0;
            p.Deaths2 = 0;
            p.getFrames().sendChatMessage(0, "Your kdr has been reset.");
        } else {
            p.getFrames().sendChatMessage(0, "<col=ff0000>This will reset your KDR and your SKULL and TRADE ability!");
            p.getFrames().sendChatMessage(0, "If you are sure about this then please do ::imsure.");

        }
    }
}