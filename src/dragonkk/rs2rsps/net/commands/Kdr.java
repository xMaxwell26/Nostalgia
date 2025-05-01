package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Kdr implements Command {

    @Override
    public void execute(String[] args, Player p) {
        p.getMask().setLastChatMessage(new ChatMessage(0, 0, "In total I have " + p.Kills2 + " kills, " + p.Deaths2 + " deaths and " + p.unsafeKills + " unsafe kills."));
        p.getMask().setChatUpdate(true);
        p.getFrames().sendChatMessage(0, "You have in total " + p.Kills2 + " kills, " + p.Deaths2 + " deaths and " + p.unsafeKills + " unsafe kills.");
    }
}