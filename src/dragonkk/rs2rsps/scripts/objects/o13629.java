package dragonkk.rs2rsps.scripts.objects;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.objectScript;

public class o13629 extends objectScript {


    @Override
    public void option1(Player p, int coordX, int coordY, int height) {
        if (!p.getCombat().isSafe(p)) {
            return;
        }
        if (p.getCombat().CombatDelay > 0) {
            p.getFrames().sendChatMessage(0, "You must wait 5 seconds to go home.");
            return;
        }
        p.getMask().getRegion().teleport(3186, 3439, 0, 0);
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
