package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

import java.util.GregorianCalendar;

public class Info implements Command {

    @Override
    public void execute(String[] args, final Player p) {
        p.getFrames().sendChatMessage(0, "Current dedicated server time: " + new GregorianCalendar().getTime() + ", your ping: " + Server.tickTimer + ".");

    }

}