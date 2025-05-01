package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Donate implements Command {

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
        p.getFrames().sendString("<col=00ff00><shad=000000>Donate info", 275, 2);
        p.getFrames().sendString("Only Donate to [RuneScape] name 'Luckies' (RSGP)", 275, 16);
        p.getFrames().sendString("Only Donate to Maxwell on Nostalgia no one else!", 275, 17);
        p.getFrames().sendString("RuneScape Gold donations will only be on the weekend", 275, 18);
        p.getFrames().sendString("RSGP prices are 2x USD (E.g 10$ = 20m RSGP)", 275, 19);
        p.getFrames().sendString("<col=FFBF00><shad=FFBF00>Solid Gold removed.", 275, 20);
        p.getFrames().sendString("<col=0000A0>5$ [Donator] - PvP armour.", 275, 21);
        p.getFrames().sendString("<col=00ff00>15$ [Extreme] - Green name, PvP armour, Divine, yell and bank in wildy.", 275, 22);
        p.getFrames().sendString("<col=800080>25$ [Trusted dicer] - Purple name + all extreme and donator benefits.", 275, 23);
        p.getFrames().sendString("<col=0000a0><shad=ff0000>50$ [Super Extreme Donator] [LIST BELOW]", 275, 24);
        p.getFrames().sendString("<col=0000a0>Blue name with shadow effect", 275, 25);
        p.getFrames().sendString("<col=0000a0>Super extreme special yell", 275, 26);
        p.getFrames().sendString("<col=0000a0>No req for anything like bh skull, chaotics, dung master cape", 275, 27);
        p.getFrames().sendString("<col=0000a0>Overloads - 125 stats on all combat skills.", 275, 28);
        p.getFrames().sendString("<col=0000a0>Onyx ring i - 8+ to everything.", 275, 29);
        p.getFrames().sendString("<col=0000a0>X2 points received per kill ::points", 275, 30);
        p.getFrames().sendString("<col=0000a0><shad=ff0000>Custom services", 275, 31);
        p.getFrames().sendString("<col=0000a0>Change yell colour - requires extreme donator [10$]", 275, 32);
        p.getFrames().sendString("<col=0000a0>Change name colour - requires super extreme [10$]", 275, 33);
        p.getFrames().sendString("<col=0000a0>No item requirments (Apart from donator items) [10$]", 275, 34);
        p.getFrames().sendString("<col=0000a0>750 SHOP Points [5$]", 275, 35);
        p.getFrames().sendString("<col=0000a0>Red skull [NO KILL REQ] [5$]", 275, 36);
        p.getFrames().sendString("<col=0000a0>Double shop points per kill [15$]", 275, 37);
        p.getFrames().sendString("<col=0000a0>Custom level 139-65535 [30$] (includes free donator rank)", 275, 38);
        p.getFrames().sendString("<col=0000a0>Any other custom things that you want just say.", 275, 39);
        p.animate(840);
        p.getFrames().sendInterface(275);
    }

}