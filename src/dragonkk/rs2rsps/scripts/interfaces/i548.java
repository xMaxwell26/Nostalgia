package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.OpenCB;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i548 extends interfaceScript {


    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        //switch(packetId) {
        switch (buttonId) {
            case 177:
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.getFrames().sendChatMessage(0, "If you're stuck on Nostalgia then try typing ::commands and ::help.");
                break;
            case 173:
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
                p.animate(840);
                break;
    /*case 175:
	p.getFrames().sendInventoryInterface(181);//Makes the old login text/button show
	p.getFrames().sendInventoryInterface(182);
	break;*/
        }
    }
}