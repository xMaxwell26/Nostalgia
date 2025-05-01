package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;
import dragonkk.rs2rsps.skills.magic.Magic;

public class i192 extends interfaceScript {

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        //AutoCasting for Ancients

        if (p.isDead()) {
            return;
        }
        if (p.Jailed) {
            p.getFrames().sendChatMessage(0, "You are jailed.");
            return;
        }
        if (p.teleblockDelay > 0) {
            p.getFrames().sendChatMessage(0, "You cannot tele while teleblocked.");
            return;
        }

        switch (buttonId) {
            case 46:
                if (!p.getCombat().isSafe(p) && p.getCombat().dangerousPVP(p) && !p.getCombat().inWild(p) && !p.getCombat().inWild(p)) {
                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                    return;
                }
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed!");
                    return;
                }
                p.WarningTeleport = 1;
                p.getFrames().sendInterface(574);
                p.getFrames().sendString("Teleport to Falador Center (Unsafe Multi)", 574, 17);
                p.getFrames().sendString("Stay here", 574, 18);
                break;
        }

        Magic.spellBookTeleport(p, 192, buttonId);
    }

    public void AutoCastOption(Player p, int config, int magicLevel) {
        if (p.getSkills().getLevel(6) < magicLevel) {
            p.getFrames().sendChatMessage(0, "You need " + magicLevel + " magic level to cast this spell.");
            return;
        }
        if (p.AutoCast) {
            p.getFrames().sendConfig(108, config);
            //p.AutoCast = false;
        } else {
            p.getFrames().sendConfig(108, 0);
            //p.AutoCast = true;
        }
    }

}
