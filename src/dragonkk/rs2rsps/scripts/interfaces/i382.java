package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i382 extends interfaceScript {

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
                if (p.WildTeleport == 0) {
                    p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                    Server.getEntityExecutor().schedule(new Task() {
                        @Override
                        public void run() {
                            p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                            p.getMask().getRegion().teleport(2963, 3696, 0, 0);
                        }
                    }, 1801);
                } else if (p.WildTeleport == 1) {
                    p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                    Server.getEntityExecutor().schedule(new Task() {
                        @Override
                        public void run() {
                            p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                            p.getMask().getRegion().teleport(3156, 3666, 0, 0);
                        }
                    }, 1801);
                } else if (p.WildTeleport == 2) {
                    p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                    Server.getEntityExecutor().schedule(new Task() {
                        @Override
                        public void run() {
                            p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                            p.getMask().getRegion().teleport(3288, 3886, 0, 0);
                        }
                    }, 1801);
                } else if (p.WildTeleport == 3) {
                    p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                    Server.getEntityExecutor().schedule(new Task() {
                        @Override
                        public void run() {
                            p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                            p.getMask().getRegion().teleport(2979, 3751, 0, 0);
                        }
                    }, 1801);
                }
                break;
        }
    }
}