package dragonkk.rs2rsps.scripts.objects;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.objectScript;
import dragonkk.rs2rsps.util.Misc;

public class o20348 extends objectScript {

    @Override
    public void option1(Player p, int coordX, int coordY, int height) {
        int randomMessage = Misc.random(100);
        int botstopMessage = Misc.random(2);
        if (p.getCombat().thievDelay > 0) {
            return;
        }
        if (p.botstop) {
            if (botstopMessage == 0) {
                p.getFrames().requestStringInput(11, "Random Message: Are you botting?");
                return;
            } else if (botstopMessage == 1) {
                p.getFrames().requestStringInput(12, "Random Message: What is the name of this server?");
                return;
            } else if (botstopMessage == 2) {
                p.getFrames().requestStringInput(13, "Random Message: Type the word 'thieving'.");
                return;
            }
            return;
        }
        if (randomMessage == 0) {
            p.botstop = true;
            p.getFrames().requestStringInput(11, "Random Message: Are you botting?");
            return;
        } else if (randomMessage == 10) {
            p.botstop = true;
            p.getFrames().requestStringInput(12, "Random Message: What is the name of this server?");
            return;
        } else if (randomMessage == 20) {
            p.botstop = true;
            p.getFrames().requestStringInput(13, "Random Message: Type the word 'thieving'.");
            return;
        }
        if (p.getSkills().getLevel(17) >= 1 && p.getSkills().getLevel(17) <= 39) {
            p.getFrames().sendChatMessage(0, "You steal 100k from the stall.");
            p.getSkills().addXp(17, 5000);
            p.getInventory().addItem(995, 100000);
        } else if (p.getSkills().getLevel(17) >= 40 && p.getSkills().getLevel(17) <= 79) {
            p.getFrames().sendChatMessage(0, "You steal 125k from the stall.");
            p.getSkills().addXp(17, 10000);
            p.getInventory().addItem(995, 125000);
        } else if (p.getSkills().getLevel(17) >= 80 && p.getSkills().getLevel(17) <= 98) {
            p.getFrames().sendChatMessage(0, "You steal 150k from the stall.");
            p.getSkills().addXp(17, 15000);
            p.getInventory().addItem(995, 150000);
        } else if (p.getSkills().getLevel(17) == 99) {
            p.getFrames().sendChatMessage(0, "You steal 200k from the stall.");
            p.getSkills().addXp(17, 30000);
            p.getInventory().addItem(995, 200000);
        }
        p.getCombat().thievDelay = 6;
        p.animate(881);
    }

    @Override
    public void option2(Player p, int coordX, int coordY, int height) {
        //p.getBank().openBank();

    }

    public void option3(Player p, int coordX, int coordY, int height) {
        //p.getBank().openBank();

    }

    @Override
    public void examine(Player p) {
        p.getFrames().sendChatMessage(0, "Thieving stall on Nostalgia.");

    }

}
