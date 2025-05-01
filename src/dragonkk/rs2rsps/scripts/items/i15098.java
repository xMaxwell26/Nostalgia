package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.player.DiceGame;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.itemScript;

public class i15098 extends itemScript {

    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p == null)
            return;
        if (p.isDead())
            return;
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;
        DiceGame.rollDice(p);

    }

}
