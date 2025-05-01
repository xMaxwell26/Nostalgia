package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Uptime implements Command {

    @Override
    public void execute(String[] args, Player p) {
        p.getFrames().sendChatMessage(0, "Uptime: " + World.hours + "h:" + World.minutes + "m:" + World.seconds + "s");

    }
}