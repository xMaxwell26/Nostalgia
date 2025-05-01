package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i137 extends interfaceScript {


    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        switch (buttonId) {
            case 278:
                p.addingFriend = true;
                p.getMask().setApperanceUpdate(true);
                break;
        }
    }
}