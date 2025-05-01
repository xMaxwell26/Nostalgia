package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Search implements Command {

    @Override
    public void execute(String[] args, final Player p) {
        if (p.getRights() == 0) {
            p.getFrames().sendChatMessage(0, "Command is in progress.");
            return;
        }
        p.getFrames().requestStringInput(11, "Nostalgia ItemDatabase Search:");
    }

}
