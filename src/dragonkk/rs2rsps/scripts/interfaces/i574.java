package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i574 extends interfaceScript {

    @Override
    public void actionButton(final Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        if (p.isDead()) {
            return;
        }
        if (p.Jailed) {
            p.getFrames().sendChatMessage(0, "You are jailed.");
            return;
        }
        switch (buttonId) {
            case 18:
                p.getFrames().sendClickableInterface(778);
                break;
            case 17:
                p.getFrames().sendClickableInterface(778);
                if (p.WarningTeleport == 0) {
                    p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                    Server.getEntityExecutor().schedule(new Task() {
                        @Override
                        public void run() {
                            p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                            p.getMask().getRegion().teleport(3092, 3494, 0, 0);
                        }
                    }, 1801);
                } else if (p.WarningTeleport == 1) {
                    p.getCombatDefinitions().doEmote(8939, 1576, 4200);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        int count = 0;

                        @Override
                        public void run() {
                            if (!p.isOnline()) {
                                this.stop();
                                return;
                            }
                            if (count++ == 0)
                                p.getMask().getRegion().teleport(2964, 3380, 0, 0);
                            else {
                                p.animate(8941);
                                p.graphics(1577);
                                this.stop();
                            }
                        }

                    }, 3, 0, 0);
                } else if (p.WarningTeleport == 2) {
                    p.getFrames().sendClickableInterface(778);
                    p.getMask().getRegion().teleport(3013, 3356, 0, 0);
                }
                break;
        }
    }
}
