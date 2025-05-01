package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class SetGender implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        int gender = Integer.parseInt(args[1]);
        if (gender < 0) {
            gender = 0;
        }
        if (gender > 1) {
            gender = 1;
        }
        if (gender == 1) {
            p.getAppearence().setGender((byte) 1);
            p.getAppearence().female();
            p.getMask().setApperanceUpdate(true);
        }
        if (gender == 0) {
            p.getAppearence().setGender((byte) 0);
            p.getAppearence().resetAppearence();
            p.getMask().setApperanceUpdate(true);
        }
    }

}
