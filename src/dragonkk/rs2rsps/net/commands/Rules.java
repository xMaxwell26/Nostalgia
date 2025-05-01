package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Rules implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        int number = 0;
        for (int i = 0; i < 316; i++) {
            p.getFrames().sendString("", 275, i);
        }
        for (Player p5 : World.getPlayers()) {
            if (p5 == null)
                continue;
            number++;
            String titles = "";
        }
        p.getFrames().sendString("<col=ff0000><shad=000000>Rules - Updated 12th Dec 2012", 275, 2);
        p.getFrames().sendString("No DDoSing", 275, 16);
        p.getFrames().sendString("No cursing", 275, 17);
        p.getFrames().sendString("No duping", 275, 18);
        p.getFrames().sendString("No glitching", 275, 19);
        p.getFrames().sendString("No farming", 275, 20);
        p.getFrames().sendString("No selling items/buying items for RSGP", 275, 21);
        p.getFrames().sendString("No scamming", 275, 22);
        p.getFrames().sendString("No spec and running", 275, 23);
        p.getFrames().sendString("No spec and tele", 275, 24);
        p.getFrames().sendString("No boosting [farming kills]", 275, 25);
        p.getFrames().sendString("No selling anything to do with rs", 275, 26);
        p.getFrames().sendString("No selling Nostalgia GP for anything", 275, 27);
        p.getFrames().sendString("No trade glitching of any type", 275, 28);
        p.getFrames().sendString("No voting more than 3 times per day", 275, 29);
        p.getFrames().sendString("No using a proxy or vpn", 275, 30);
        p.getFrames().sendString("No spec and run", 275, 31);
        p.getFrames().sendString("No luring with the command ::yell", 275, 32);
        p.getFrames().sendString("No selling anything to do with Nostalgia", 275, 33);
        p.getFrames().sendString("No account sharing", 275, 34);
        p.getFrames().sendString("No phishing", 275, 35);
        p.getFrames().sendString("No spec and pray rush", 275, 36);
        p.getFrames().sendString("No advertising other servers/websites", 275, 37);
        p.getFrames().sendString("No spamming within 1-3 seconds with an auto typer", 275, 38);
        p.getFrames().sendString("No botting/mass clicking the thiev stall", 275, 39);
        p.getFrames().sendString("<col=ff0000>Breaking any of the rules above will result in either an</col>", 275, 40);
        p.getFrames().sendString("<col=ff0000>Account Suspension/Termination, Warnings, Jail, Mute or IPban</col>", 275, 41);
        //p.getFrames().sendString("To appeal to anything go to www.Nostalgia.co.uk", 275, 42); //TODO
        p.animate(840);
        p.getFrames().sendInterface(275);
    }

}