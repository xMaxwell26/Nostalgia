package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i589 extends interfaceScript {

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        switch (buttonId) {
            case 12://Clan Chat Options:
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.getFrames().sendInterface(590);
                p.getFrames().sendString(World.getClanManager().getClanName(p.getUsername()), 590, 22);
                break;
            case 15:
                World.getClanManager().leaveClan(p);
                //p.getClanSettings().setCurrentClan(null);
                break;
            case 17:
                World.getClanManager().toggleLootshare(p);
                break;
        }
    }
}
