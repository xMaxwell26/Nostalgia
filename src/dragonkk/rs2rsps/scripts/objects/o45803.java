package dragonkk.rs2rsps.scripts.objects;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.objectScript;

public class o45803 extends objectScript {

    @Override
    public void option1(final Player p, int coordX, int coordY, int height) {
        p.getMask().getRegion().teleport(3186, 3440, 0, 0);
        p.getFrames().sendChatMessage(0, "You step through the portal and teleport back home.");

    }

    @Override
    public void option2(Player p, int coordX, int coordY, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void examine(Player p) {
        // TODO Auto-generated method stub

    }

}
