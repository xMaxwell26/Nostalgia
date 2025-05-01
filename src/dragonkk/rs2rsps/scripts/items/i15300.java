package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.itemScript;

public class i15300 extends itemScript {

    @Override
    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You cannot drink this potion here.");
            return;
        }
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;
        if (System.currentTimeMillis() - p.getCombatDefinitions().getLastPot() < 0)
            return;
        p.getInventory().deleteItem(15300, 1, slot);
        p.getInventory().addItem(15301, 1, slot);
        p.getCombatDefinitions().specpercentage += 25;
        p.animate(829);
        p.getCombatDefinitions().setLastPot(System.currentTimeMillis() + 1000);
        p.getCombatDefinitions().setLastFood(System.currentTimeMillis() + 1000);
    }
}
