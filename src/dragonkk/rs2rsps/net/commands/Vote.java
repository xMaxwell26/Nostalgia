package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Vote implements Command {

    @Override
    public void execute(String[] args, Player p) {
        //p.getFrames().sendChatMessage(0, "Vote link: www.spawnscape.org/vote.");
        p.getFrames().sendChatMessage(0,"Voting has been disabled temporarily"); //TODO
    }

}
