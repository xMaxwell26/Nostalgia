package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.scripts.itemScript;

public class i3042 extends itemScript {

    @Override
    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;
        if (System.currentTimeMillis() - p.getCombatDefinitions().getLastPot() < 0)
            return;
        p.getInventory().deleteItem(3042, 1, slot);
        p.getInventory().addItem(3044, 1, slot);
        p.getSkills().set(Skills.MAGIC, p.getSkills().getLevelForXp(Skills.MAGIC) + 5 + Math.round(p.getSkills().getLevelForXp(Skills.MAGIC) * 1 / 100));
        p.animate(829);
        p.getCombatDefinitions().setLastPot(System.currentTimeMillis() + 1000);
        p.getCombatDefinitions().setLastFood(System.currentTimeMillis() + 1000);
    }
}
