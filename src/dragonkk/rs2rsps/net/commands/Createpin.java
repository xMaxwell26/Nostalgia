package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Createpin implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (p.getCombat().hasTarget() || p.getCombat().combatWithDelay > 0 || p.getSkills().isDead() || p.getCombat().delay > 0) {
            p.getFrames().sendChatMessage(0, "You can't make a bank pin while in combat.");
            return;
        }
        if (p.hasPin && p.BankPinNumber > 0) {
            p.getFrames().sendChatMessage(0, "You already have a bank pin.");
        } else if (!p.hasPin && !p.needConfirm) {
            p.getFrames().requestIntegerInput(5, "Step 1: Create your Pin Number:");
            p.getFrames().sendChatMessage(0, "Creating your new bank pin.");
        } else if (!p.hasPin && p.needConfirm) {
            p.getFrames().requestIntegerInput(6, "Step 2: Confirm your Pin Number:");
            p.getFrames().sendChatMessage(0, "Confirming your new bank pin.");
        } else {
            p.getFrames().sendChatMessage(0, "Something is wrong here.");
        }
    }
}
