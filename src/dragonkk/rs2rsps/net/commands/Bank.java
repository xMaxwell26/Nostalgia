package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Bank implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (p.getCombat().hasTarget() || p.getCombat().combatWithDelay > 0 || p.getSkills().isDead() || p.getCombat().delay > 0) {
            p.getFrames().sendChatMessage(0, "You can't bank while in combat.");
            return;
        }
        if (!p.getCombat().isSafe(p) && !p.extremeDonator) {
            p.getFrames().sendChatMessage(0, "You must be a extreme donator to bank in a non SafeZone.");
            return;
        }
        if (p.curseDelay > 0) {
            p.getFrames().sendChatMessage(0, "You are cursed!");
            return;
        }
        if (p.entered && p.hasPin) {
            p.getBank().openBank();
            p.getInventory().refresh();
        } else if (p.hasPin && p.BankPinNumber > 0) {
            p.getFrames().requestIntegerInput(7, "Please enter your bank pin:");
        } else {
            p.getBank().openBank();
        }
    }

}