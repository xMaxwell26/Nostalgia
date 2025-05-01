package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.OpenCB;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i746 extends interfaceScript {

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        switch (buttonId) {
            case 176:
                if (!p.getCombat().isSafe(p)) {
                    p.getFrames().sendChatMessage(0, "You can't click this when not in a safezone.");
                    return;
                }
                if (p.getTradeSession() != null) {
                    p.getTradeSession().tradeFailed();
                }
                if (p.getSkills().playerDead)
                    return;
                OpenCB.open(p);
                break;
        }
    }

}
