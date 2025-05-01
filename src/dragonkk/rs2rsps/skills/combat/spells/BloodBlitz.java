package dragonkk.rs2rsps.skills.combat.spells;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.combat.MagicInterface;
import dragonkk.rs2rsps.skills.combat.RuneRequirements;
import dragonkk.rs2rsps.util.CombatManager;
import dragonkk.rs2rsps.util.Misc;

public class BloodBlitz implements MagicInterface {

    @Override
    public void execute(final Player p, final Player opp) {
        double maxHit = 240 + p.bonusmagicdmg;
        if (opp.getCombat().isSafe(opp)) return;
        if (p.Jailed) {
            p.getFrames().sendChatMessage(0, "You are jailed.");
            return;
        }
        if (!RuneRequirements.hasRunes(p, "BLOOD_BLITZ")) {
            p.getFrames().sendChatMessage(0, "You don't have enough runes to cast this spell.");
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
        final int hit = p.getCombat().getMagicHit(p, opp, Misc.random((int) maxHit));
        p.getCombat().soulSplit(p, opp, hit);
        p.graphics(-1);
        p.graphics2(-1);
        p.animate(1978);
        p.graphics(366);
        p.getInventory().deleteItem(560, 2);
        p.getInventory().deleteItem(565, 4);
        p.getSkills().sendCounter(hit, true);
        if (opp.getCombat().immuneDelay == 0 & hit != 0) {
            opp.getCombat().removeTarget();
            opp.getWalk().reset(true);
            opp.getCombat().freezeDelay = 30;
            opp.getCombat().immuneDelay = 38;
        }
        if (hit != 0) {
            p.getSkills().heal(hit / 4);
            opp.getFrames().sendChatMessage(0, "You feel the life sucked out of you.");
        } else p.getSkills().sendCounter(52, true);
        final boolean sucessfulCast = hit != 0;
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                opp.animate(CombatManager.getDefenceEmote(opp));
                if (sucessfulCast) {
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {
                            opp.hit(hit, p);
                            p.getSkills().heal(hit / 4);
                            if (opp.getCombat().vengeance) if (hit > 0) opp.getCombat().applyVengeance(
                                    Math.floor(hit * 0.75), p, opp);
                        }
                    }, 1, 0);
                    opp.graphics(373);
                } else opp.graphics2(85);
                this.stop();
            }
        }, 2, 0);
    }


}
