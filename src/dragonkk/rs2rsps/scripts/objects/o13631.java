package dragonkk.rs2rsps.scripts.objects;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.objectScript;

public class o13631 extends objectScript {


    @Override
    public void option1(Player p, int coordX, int coordY, int height) {
        if (!p.getCombat().isSafe(p)) {
            return;
        }
        p.WarningTeleport = 2;
        p.getFrames().sendInterface(574);
        p.getFrames().sendString("Teleport to Falador (Unsafe PvP)", 574, 17);
        p.getFrames().sendString("Stay here", 574, 18);
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
