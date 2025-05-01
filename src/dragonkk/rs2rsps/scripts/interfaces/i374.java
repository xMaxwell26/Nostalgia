package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.AdminCP;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

/**
 * Created by Jet Kai using IntelliJ IDEA.
 * Date: 08/04/12
 * Time: 05:05
 */
public class i374 extends interfaceScript {

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't tele in a non-safe zone.");
            return;
        }
        p.getFrames().sendString("Close Extra", 374, 5);
        p.getFrames().sendString("JMOD Zone", 374, 11);
        p.getFrames().sendString("Appearance", 374, 12);
        p.getFrames().sendString("Donator Zone", 374, 13);
        p.getFrames().sendString("JMOD Options", 374, 15);
        p.getFrames().sendString("Toggle Name", 374, 14);
        switch (buttonId) {
            case 5:
                p.getFrames().closeInventoryInterface();
                break;
            case 11:
                p.getMask().getRegion().teleport(2845, 5221, 0, 0);
                p.getFrames().closeInventoryInterface();
                break;
            case 12:
                if (p.getRights() == 0) {
                    p.getFrames().sendChatMessage(0, "This feature isn't ready yet for players.");
                    return;
                }
                p.getFrames().sendInterface(900);
                break;
            case 13:
                if (!p.isDonator) {
                    p.getFrames().sendChatMessage(0, "You are not a donator so you can't do this command.");
                } else {
                    if (Math.random() * 100 >= 50) {
                        p.getMask().getRegion().teleport(2443, 5529, 0, 0);
                        p.getFrames().closeInventoryInterface();
                        p.getFrames().sendChatMessage(0, "You were teleported randomly to the north train.");
                    } else {
                        p.getMask().getRegion().teleport(2443, 5526, 0, 0);
                        p.getFrames().closeInventoryInterface();
                        p.getFrames().sendChatMessage(0, "You were teleported randomly to the south train.");
                    }
                }
                break;
            case 15:
                if (p.getRights() < 2) {
                    p.getFrames().sendChatMessage(0, "You don't have permission to do this.");
                    return;
                }
                p.getFrames().closeInventoryInterface();
                AdminCP.open(p);
                break;
            case 14:
                if (p.addingFriend) {
                    p.addingFriend = false;
                    p.getMask().setApperanceUpdate(true);
                    p.getFrames().sendChatMessage(0, "Custom name toggled on.");
                } else {
                    p.addingFriend = true;
                    p.getMask().setApperanceUpdate(true);
                    p.getFrames().sendChatMessage(0, "Custom name toggled off.");
                }
        }

    }
}
