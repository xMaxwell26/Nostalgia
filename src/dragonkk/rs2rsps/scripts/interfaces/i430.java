package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;
import dragonkk.rs2rsps.skills.combat.RuneRequirements;

public class i430 extends interfaceScript {

    @Override
    public void actionButton(final Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        if (p.isDead()) {
            return;
        }
        if (p.Jailed) {
            p.getFrames().sendChatMessage(0, "You are jailed.");
            return;
        }
        if (p.curseDelay > 0) {
            p.getFrames().sendChatMessage(0, "Vengeance has been disabled by the curse effect.");
            return;
        }
        switch (buttonId) {
            case 36:
                if (p.getSkills().level[6] < 94) {
                    p.getFrames().sendChatMessage(0, "You need a magic level of 94 to cast vengeance");
                    return;
                }
                if (!RuneRequirements.hasRunes(p, "VENGEANCE")) {
                    p.getFrames().sendChatMessage(0, "You don't have enough runes to cast this spell.");
                    return;
                }
                if (p.getCombat().vengeance) {
                    p.getFrames().sendChatMessage(0, "You already have vengeance casted.");
                    return;
                }
                if (p.getCombat().vengDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You need to wait 30 seconds before you can cast vengeance again.");
                    return;
                }
                p.animate(4410);
                p.graphics2(726);
                p.getSkills().addXp(6, 112);
                p.getCombat().vengeance = true;
                p.getCombat().vengDelay = 60;
                break;
            case 38: //Home tele
                if (!p.getCombat().isSafe(p) && p.getCombat().dangerousPVP(p) && !p.getCombat().inWild(p)) {
                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                    return;
                }
                if (p.teleblockDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You cannot tele while teleblocked.");
                    return;
                }
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                Server.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                        p.getMask().getRegion().teleport(3186, 3439, 0, 0);
                    }
                }, 1801);
                break;
        }
    }

}
