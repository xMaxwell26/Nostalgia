package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.itemScript;

public class i391 extends itemScript {

    @Override
    public void option1(Player p, int itemId, int interfaceId, int slot) {
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
        p.getInventory().deleteItem(391, 1, slot);
        p.animate(829);
        p.getSkills().heal(220);
        p.getCombatDefinitions().setLastFood(System.currentTimeMillis() + 1200);
        p.getCombat().delay += 2;
        p.getFrames().sendChatMessage(0, "You eat the Manta ray.");
    }

}
