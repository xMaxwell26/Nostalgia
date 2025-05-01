package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i750 extends interfaceScript {

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        switch (packetId) {
            case 79: //Option 1 (click)
                switch (buttonId) {
                    case 1:
                        p.getWalk().setRunToggled(!p.getWalk().isRunToggled());
                        break;
                }
                break;
            case 24: //Option 2
                switch (buttonId) {
                    case 1:
                        p.getWalk().reset(true);
                        p.rest();
                        break;
                }
                break;
        }
    }

}
