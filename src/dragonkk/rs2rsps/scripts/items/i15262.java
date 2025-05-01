package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.itemScript;

public class i15262 extends itemScript {

    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p == null)
            return;
        if (p.isDead())
            return;
        if (p.getInventory().getContainer().get(slot) == null) {
            return;
        }
        if (p.getInventory().getContainer().get(slot).getId() != itemId) {
            return;
        }
        p.getInventory().deleteItem(15262, 1, slot);
        p.getInventory().addItem(12530, 400);
        p.getFrames().sendChatMessage(0, "You open the Spirit Shard Pack and receive 400 Spirit Shards.");
    }

}
