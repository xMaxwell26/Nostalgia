package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.net.Command;

public class Resetcounter implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        if (p.sure) {
            for (int i = 0; i < 7; i++) {
                p.getSkills().setXp(i, Skills.SMALL_EXP);
            }
            p.xpGained = 0;
            p.getSkills().sendSkillLevels();
            p.getFrames().sendChatMessage(0, "XP bar has been reset.");
            p.sure = false;
        } else {
            p.getFrames().sendChatMessage(0, "Please type ::imsure to do this command.");
            p.getFrames().sendChatMessage(0, "<col=ff0000>Warning, this will make your xp on your xp bar = 0!");
        }
    }
}
