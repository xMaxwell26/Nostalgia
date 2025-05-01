package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i993 extends interfaceScript {

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        p.LastIp = 0;
        switch (buttonId) {
            case 257:
                p.getFrames().sendChatMessage(0, "Melee selected.");
                p.selectedMelee();
                break;
            case 242:
                p.getFrames().sendChatMessage(0, "Ranged Selected.");
                p.selectedRanged();
                break;
            case 227:
                p.LastIp = 0;
                p.getFrames().sendChatMessage(0, "Magic selected.");
                p.selectedMagic();
                break;
            case 212:
                p.getFrames().sendInterface(993);
                p.getFrames().sendChatMessage(0, "Class not available.");
                break;
            case 137:
            case 44:
            case 86:
            case 139:
            case 46:
            case 88:
                p.getFrames().sendChatMessage(0, "Nothing intresting happens.");
                break;
            case 174://exit
                //p.getFrames().sendInterface(993);
                p.getFrames().sendChatMessage(0, "Type ::starter to open up the starter screen again.");
                break;
            case 190://option reset
                p.getFrames().sendInterface(993);
                p.getFrames().sendChatMessage(0, "You reset the options.");
                p.meleeDelay--;
                p.magicDelay--;
                p.rangedDelay--;
                break;
        }
    }
}
