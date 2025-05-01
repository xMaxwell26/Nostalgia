package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.itemScript;

public class i8007 extends itemScript {

    @Override
    public void option1(final Player p, final int itemId, final int interfaceId, final int slot) {
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;
        if (p.isDead()) {
            return;
        }
        if (p.Jailed) {
            p.getFrames().sendChatMessage(0, "You are jailed.");
            return;
        }
        if (p.teleblockDelay > 0) {
            p.getFrames().sendChatMessage(0, "You cannot tele while teleblocked.");
            return;
        }
        if (p.tabbing > 0) {
            return;
        }
        p.animate(4069);
        p.tabbing = 5;
        GameLogicTaskManager.schedule(new GameLogicTask() {
            int count = 0;

            @Override
            public void run() {
                if (!p.isOnline()) {
                    this.stop();
                    return;
                }
                if (count++ == 0) {
                    p.animate(4071);
                    p.graphics(678);
                    p.getInventory().deleteItem(8007, 1, slot);
                } else {
                    p.getMask().getRegion().teleport(3186, 3439, 0, 0);
                    p.animate(-1);
                    this.stop();
                }
            }

        }, 1, 0, 0);
    }
}