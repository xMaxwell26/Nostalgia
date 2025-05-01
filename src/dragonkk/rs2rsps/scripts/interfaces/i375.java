package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

/**
 * Created by Jet Kai using IntelliJ IDEA.
 * Date: 08/04/12
 * Time: 05:05
 */
public class i375 extends interfaceScript {

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        switch (buttonId) {
            case 3:
                p.isMorphed = false;
                p.getFrames().sendChatMessage(0, "You are back to normal.");
                p.getAppearence().setNpcType((short) -1);
                p.getMask().setApperanceUpdate(true);
                p.isNpc = false;
                p.getFrames().closeInventoryInterface();
                break;
        }
    }
}
