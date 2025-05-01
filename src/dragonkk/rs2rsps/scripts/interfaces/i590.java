package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i590 extends interfaceScript {

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        if (p.getSkills().playerDead)
            return;
        if (p.getCombat().combatWithDelay > 0)
            return;
        if (p.getCombat().delay > 0)
            return;
        switch (buttonId) {
            case 7:
                p.getFrames().sendLogout();
                break;
            case 9:
                p.getFrames().sendLogout();
                break;
        }
        if (buttonId == 22) {
            switch (packetId) {
                case 79: // prefix
                    p.getFrames().requestStringInput(0, "Enter clan prefix:");
                    break;
                case 24: // disable

                    break;
            }
        }
    }

}
