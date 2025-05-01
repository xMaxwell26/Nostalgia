package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.scripts.itemScript;

public class i15334 extends itemScript {

    @Override
    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (!p.superextremeDonator) {
            p.getFrames().sendChatMessage(0, "You must be a [Super Extreme] to use this potion.");
            return;
        }
        if (p.overloadstats > 0) {
            p.getFrames().sendChatMessage(0, "You need to wait 3 minutes to drink this again.");
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
        p.getInventory().deleteItem(15334, 1, slot);
        p.getInventory().addItem(15335, 1, slot);
        p.overload = 11; //2sec extra for drink
        p.overloadstats = 500; //5min lasted.
        p.getSkills().set(Skills.STRENGTH, p.getSkills().getLevelForXp(Skills.STRENGTH) + 26);
        p.getSkills().set(Skills.ATTACK, p.getSkills().getLevelForXp(Skills.ATTACK) + 26);
        p.getSkills().set(Skills.DEFENCE, p.getSkills().getLevelForXp(Skills.DEFENCE) + 26);
        p.getSkills().set(Skills.RANGE, p.getSkills().getLevelForXp(Skills.RANGE) + 23);
        p.getSkills().set(Skills.MAGIC, p.getSkills().getLevelForXp(Skills.MAGIC) + 7);
        p.animate(829);
        p.getCombatDefinitions().setLastPot(System.currentTimeMillis() + 1000);
        p.getCombatDefinitions().setLastFood(System.currentTimeMillis() + 1000);
    }

}
