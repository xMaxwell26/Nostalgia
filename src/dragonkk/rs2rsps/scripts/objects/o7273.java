package dragonkk.rs2rsps.scripts.objects;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.objectScript;

public class o7273 extends objectScript {

    @Override
    public void option1(final Player p, int coordX, int coordY, int height) {
        p.getFrames().sendChatMessage(0, "<img=4>Test.");


    }

    @Override
    public void option2(Player p, int coordX, int coordY, int height) {
        p.getFrames().sendChatMessage(0, "<img=4>Test.");

    }

    @Override
    public void examine(Player p) {
        p.getFrames().sendChatMessage(0, "<img=4>Test.");

    }

}
