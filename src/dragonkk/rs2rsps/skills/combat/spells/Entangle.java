package dragonkk.rs2rsps.skills.combat.spells;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.combat.MagicInterface;
import dragonkk.rs2rsps.skills.combat.RuneRequirements;
import dragonkk.rs2rsps.util.CombatManager;
import dragonkk.rs2rsps.util.Misc;

public class Entangle implements MagicInterface {

    @Override
    public void execute(final Player p, final Player opp) {
        double maxHit = 100;
        if (opp.getCombat().isSafe(opp)) return;
        if (p.Jailed) {
            p.getFrames().sendChatMessage(0, "You are jailed.");
            return;
        }
        if (p.getEquipment().contains(4675)) maxHit *= 1.1;
        if (p.getEquipment().contains(18335)) maxHit *= 1.15;
        if (p.getEquipment().contains(18355)) maxHit *= 1.20;
        if (p.getEquipment().contains(6914)) maxHit *= 1.20;
        if (p.getEquipment().contains(15486)) maxHit *= 1.15;
        if (p.getEquipment().contains(19710)) maxHit *= 1.05;
        if (p.getEquipment().contains(18741)) maxHit *= 1.01;
        if (p.getEquipment().contains(18740)) maxHit *= 1.03;
        if (p.getEquipment().contains(18742)) maxHit *= 1.04;
        if (p.getEquipment().contains(18743)) maxHit *= 1.06;
        if (!RuneRequirements.hasRunes(p, "ENTANGLE")) {
            p.getFrames().sendChatMessage(0, "You don't have enough runes to cast this spell.");
            return;
        }
        final int hit = p.getCombat().getMagicHit(p, opp, Misc.random((int) maxHit));
        p.getCombat().soulSplit(p, opp, hit);
        p.animate(710);
        p.graphics2(177);
        if (opp.getCombat().immuneDelay == 0 & hit != 0) {
            opp.getCombat().removeTarget();
            opp.getWalk().reset(true);
            opp.getCombat().freezeDelay = 20;
            opp.getCombat().immuneDelay = 28;
            opp.getFrames().sendChatMessage(0, "You have been entangled!");
        }
        if (hit != 0) p.getSkills().sendCounter(100, true);
        else p.getSkills().sendCounter(52, true);

        final boolean sucessfulCast = hit != 0;
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                opp.animate(CombatManager.getDefenceEmote(opp));
                if (sucessfulCast) {
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {
                            opp.hit(50, p);
                            if (opp.getCombat().vengeance) if (hit > 0) opp.getCombat().applyVengeance(
                                    Math.floor(hit * 0.75), p, opp);
                        }
                    }, 1, 0);
                    opp.graphics2(179);
                } else opp.graphics2(85);
                this.stop();
            }
        }, 2, 0);
    }

}
