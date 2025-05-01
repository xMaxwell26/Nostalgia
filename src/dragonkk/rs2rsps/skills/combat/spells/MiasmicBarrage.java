package dragonkk.rs2rsps.skills.combat.spells;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.combat.MagicInterface;
import dragonkk.rs2rsps.skills.combat.RuneRequirements;
import dragonkk.rs2rsps.util.CombatManager;
import dragonkk.rs2rsps.util.Misc;

public class MiasmicBarrage implements MagicInterface {

    @Override
    public void execute(final Player p, final Player opp) {
        double maxHit = 200;
        boolean multiCombat = false;
        if (opp.getCombat().isSafe(opp)) return;
        if (!p.getCombat().Multi(p) && !opp.getCombat().Multi(opp)) multiCombat = true;
        if (p.Jailed) {
            p.getFrames().sendChatMessage(0, "You are jailed.");
            return;
        }
        if (!RuneRequirements.hasRunes(p, "MIASMIC_BARRAGE")) {
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
            /*if(hit == 0) {
            p.getSkills().sendCounter(52, 52, true);
            }	 */
        final int hit = p.getCombat().getMagicHit(p, opp, Misc.random((int) maxHit));
        p.getCombat().soulSplit(p, opp, hit);
        p.animate(1979);
        opp.graphics(-1);
        boolean orbs = false;
        if (hit != 0) if (opp.getCombat().immuneDelay > 0) orbs = true;
        else {
            opp.getCombat().removeTarget();
            opp.getWalk().reset(true);
            opp.getCombat().freezeDelay = 40;
            opp.getCombat().immuneDelay = 48;
        }
        else p.getSkills().sendCounter(52, true);
        p.getSkills().sendCounter(hit, true);
        final boolean hasOrb = orbs;
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
                            if (opp.getCombat().vengeance) if (hit > 0) opp.getCombat().applyVengeance(
                                    Math.floor(hit * 0.75), p, opp);
                        }
                    }, 0, 0);
                    if (hasOrb) opp.graphics2(1677);
                    else opp.graphics(369);
                } else opp.graphics2(85);
                this.stop();
            }
        }, 2, 0);
    }

}
