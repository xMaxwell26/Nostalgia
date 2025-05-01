package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Setlevel implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        //p.getFrames().sendChatMessage(0, "Setlevel has been changed.");
        //p.getFrames().sendChatMessage(0, "To change your level you must go on the skills tab and click the skill to level.");
        int level = Integer.parseInt(args[1]);
        int amount = Integer.parseInt(args[2]);
        if (p.getCombat().delay > 0) {
            p.getFrames().sendChatMessage(0, "You can't set stat's while in combat.");
            return;
        }
        if (amount > 99) {
            amount = 99;
        }
        if (amount < 1) {
            amount = 1;
        }
        if (level == 17) {
            return;
        }
        if (level == 3 && amount < 10) {
            p.getFrames().sendChatMessage(0, "You can't go under 10 HP anymore.");
            return;
        }
        if (level > 7 && level < 22 || level == 24) {
            p.getFrames().sendChatMessage(0, "You can't set level in skills.");
            return;
        }
        int xp = p.getSkills().getXPForLevel(amount);
        p.getSkills().set(level, amount);
        p.getSkills().setXp(level, xp);
    }

}
