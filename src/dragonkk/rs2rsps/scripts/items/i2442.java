package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.scripts.itemScript;

public class i2442 extends itemScript {

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
        p.getInventory().deleteItem(2442, 1, slot);
        p.getInventory().addItem(163, 1, slot);
        p.getSkills().set(Skills.DEFENCE, p.getSkills().getLevelForXp(Skills.DEFENCE) + 5 + Math.round(p.getSkills().getLevelForXp(Skills.DEFENCE) * 15 / 100));
        p.animate(829);
        p.getCombatDefinitions().setLastPot(System.currentTimeMillis() + 1000);
        p.getCombatDefinitions().setLastFood(System.currentTimeMillis() + 1000);
    }
}
