package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.itemScript;

public class i385 extends itemScript {

    @Override
    public void option1(final Player p, int itemId, int interfaceId, final int slot) {
        if (p == null)
            return;
        if (p.isDead())
            return;
        if (p.getInventory().getContainer().get(slot) == null) {
            //p.getFrames().sendChatMessage(0,"Inventory null, relog or bank.");
            return;
        }
        if (p.getInventory().getContainer().get(slot).getId() != itemId) {
            //p.getFrames().sendChatMessage(0,"Item null, relog or bank.");
            return;
        }
        if (System.currentTimeMillis() - p.getCombatDefinitions().getLastFood() < 0)
            return;
        p.animate(829);
        p.getCombatDefinitions().setLastFood(System.currentTimeMillis() + 1000);
        p.getCombat().delay += 2;
        p.getInventory().deleteItem(385, 1, slot);
        p.getSkills().heal(200);
        p.getFrames().sendChatMessage(0, "You eat the Shark.");
    }
}
