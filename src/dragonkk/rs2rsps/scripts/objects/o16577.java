package dragonkk.rs2rsps.scripts.objects;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.objectScript;

public class o16577 extends objectScript {


    @Override
    public void option1(Player p, int coordX, int coordY, int height) {
        if (!p.isDonator) {
            p.getFrames().sendChatMessage(0, "You need to be a donator to use this portal.");
        } else {
            if (Math.random() * 100 >= 50) {
                p.getMask().getRegion().teleport(2443, 5529, 0, 0);
                p.getFrames().sendChatMessage(0, "You were teleported randomly to the north train.");
            } else {
                p.getMask().getRegion().teleport(2443, 5526, 0, 0);
                p.getFrames().sendChatMessage(0, "You were teleported randomly to the south train.");
            }
        }
    }

    @Override
    public void option2(Player p, int coordX, int coordY, int height) {
    }


    public void option3(Player p, int coordX, int coordY, int height) {
    }


    @Override
    public void examine(Player p) {
        p.getFrames().sendChatMessage(0, "Nostalgia's Bank in Varrock.");

    }

}
