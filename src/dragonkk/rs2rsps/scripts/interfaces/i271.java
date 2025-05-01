package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i271 extends interfaceScript {

    @Override
    public void actionButton(final Player p, final int packetId, final int buttonId, final int buttonId2, final int buttonId3) {
        if (buttonId == 8) {
            GameLogicTaskManager.schedule(new GameLogicTask() {
                @Override
                public void run() {
                    p.getPrayer().switchPrayer(buttonId2);
                    this.stop();
                }
            }, 0, 0);

        }
    }
}
