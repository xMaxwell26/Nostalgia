package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i900 extends interfaceScript {

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        switch (buttonId) {
            case 20:
                p.getAppearence().female();
                p.getMask().setApperanceUpdate(true);
                break;
            case 15:
            case 27:
                p.getAppearence().resetAppearence();
                p.getMask().setApperanceUpdate(true);
                break;
            case 81:
                p.getFrames().sendClickableInterface(778);
                break;
        }
    }

}
