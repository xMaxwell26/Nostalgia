package dragonkk.rs2rsps.scripts.objects;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.objectScript;

public class o46935 extends objectScript {


    @Override
    public void option1(Player p, int coordX, int coordY, int height) {
        if (p.getRights() == 0 && !p.isForumMod) {
            p.getFrames().sendChatMessage(0, "You don't have permission to use this portal.");
            return;
        }
        p.getMask().getRegion().teleport(2064, 4385, 0, 0);
        p.getFrames().sendChatMessage(0, "You teleport to StaffZone.");
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
