package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Points implements Command {

    @Override
    public void execute(String[] args, Player p) {
        p.getMask().setLastChatMessage(new ChatMessage(0, 0, "I have " + p.Points + " POINTS to spend in the shop!"));
        p.getMask().setChatUpdate(true);
        p.getFrames().sendChatMessage(0, "You have " + p.Points + " POINTS to spend in the shop.");
    }
}