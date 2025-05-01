package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Deletepin implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (p.getCombat().hasTarget() || p.getCombat().combatWithDelay > 0 || p.getSkills().isDead() || p.getCombat().delay > 0) {
            p.getFrames().sendChatMessage(0, "You can't make a bank pin while in combat.");
            return;
        }
        if (p.entered) {
            p.getFrames().requestStringInput(1, "Pin Deletion: Please type 'yes' to delete the pin.");
        } else if (p.hasPin) {
            p.getFrames().sendChatMessage(0, "Please enter your pin before continuing");
        } else {
            p.getFrames().sendChatMessage(0, "You don't have a bank pin.");
        }
    }
}
