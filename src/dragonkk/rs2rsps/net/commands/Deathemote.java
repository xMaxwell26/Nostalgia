package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Deathemote implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        int death = Integer.parseInt(args[1]);
        if (death > 4 || death < 0) {
            p.getFrames().sendChatMessage(0, "No such death emote.");
            return;
        } else if (death == 4) {
            p.deathemote = 4;
            p.getFrames().sendChatMessage(0, "Death emote set to Custom 2.");
            return;
        } else if (death == 3) {
            p.deathemote = 3;
            p.getFrames().sendChatMessage(0, "Death emote set to Custom 1.");
            return;
        } else if (death == 2) {
            p.deathemote = 2;
            p.getFrames().sendChatMessage(0, "Death emote set to [525]");
            return;
        } else if (death == 1) {
            p.deathemote = 1;
            p.getFrames().sendChatMessage(0, "Death emote set to [317]");
            return;
        } else if (death == 0) {
            p.deathemote = 0;
            p.getFrames().sendChatMessage(0, "Death emote set to [614]");
            return;
        }
    }
}