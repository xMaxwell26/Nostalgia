package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i594 extends interfaceScript {

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        switch (buttonId) {
            case 258://Report abuse
                //Report.accounts(p);
                break;
            case 259://Report abuse
                //p.getFrames().sendChatMessage(0, "This report is not supported.");
                break;
            case 260://Report abuse
                //Report.staffin(p);
                break;
        }
    }
}
