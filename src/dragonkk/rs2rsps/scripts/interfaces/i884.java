package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i884 extends interfaceScript {
    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        switch (buttonId) {
            case 4:
                p.getCombatDefinitions().switchSpecial();
                break;
            case 11:
                p.getFrames().sendConfig2(43, 0);
                p.getCombatDefinitions().setAttackStyle((byte) 3);
                break;
            case 12:
                p.getFrames().sendConfig2(43, 1);
                p.getCombatDefinitions().setAttackStyle((byte) 3);
                break;
            case 13:
                p.getFrames().sendConfig2(43, 2);
                p.getCombatDefinitions().setAttackStyle((byte) 2);
                break;
            case 14:
                p.getFrames().sendConfig2(43, 3);
                p.getCombatDefinitions().setAttackStyle((byte) 1);
                break;
            case 15:
                p.AutoRetaliate = !p.AutoRetaliate;
                p.getFrames().sendConfig(172, p.AutoRetaliate ? 0 : 1);
                break;
            default:
                p.getFrames().sendChatMessage(0, "ButtonId: " + buttonId);
                break;
        }
    }
}
