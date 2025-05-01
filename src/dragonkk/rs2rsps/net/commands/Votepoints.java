package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Votepoints implements Command {

    @Override
    public void execute(String[] args, Player p) {
        p.getMask().setLastChatMessage(new ChatMessage(0, 0, "I have [" + p.votePoints + "] VOTE points."));
        p.getMask().setChatUpdate(true);
        p.getFrames().sendChatMessage(0, "You have " + p.votePoints + " vote points.");
    }
}