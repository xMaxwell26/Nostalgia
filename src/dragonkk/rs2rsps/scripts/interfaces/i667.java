package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.Scripts;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i667 extends interfaceScript {

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        switch (buttonId) {
            case 0:
                Scripts.invokeItemScript((short) buttonId3).examine(p, buttonId3, buttonId2);
                break;
            default:
                System.out.println("packetId: " + packetId + ", Buttonid: " + buttonId);
                break;
        }
    }
}
