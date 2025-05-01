package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.net.Command;

public class Master implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        if (p.getSkills().getCombatLevel() == 138) {
            p.getFrames().sendChatMessage(0, "You are already level 138.");
            return;
        }
        for (int i = 0; i < 7; i++) {
            p.getSkills().setXp(i, Skills.SMALL_EXP);
            p.getSkills().set(i, 99);
            p.getSkills().set(6, 99);
            p.getSkills().setXp(6, Skills.SMALL_EXP);
            p.getSkills().set(23, 99);
            p.getSkills().setXp(23, Skills.SMALL_EXP);
            p.getSkills().heal(990);
            p.getFrames().sendChatMessage(0, "You can also choose to set your own level by clicking the tabs in the skill menu.");
        }

    }

}
