package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.util.Serializer;

public class Backup implements Command {


    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        if (p.getTradeSession() != null) {
            return;
        }
        if (Server.updateTime > 0) {
            p.getFrames().sendChatMessage(0, "A System Update is being processed.");
            return;
        }
        p.getFrames().sendClickableInterface(257);
        Serializer.BackupAccount(p);
        Serializer.Backup2Account(p);
        p.getMask().setLastChatMessage(new ChatMessage(0, 0, "I HAVE BACKEDUP MY ACCOUNT!"));
        p.getMask().setChatUpdate(true);
        p.getFrames().sendChatMessage(0, "Your backup was completed.");
        p.getFrames().sendChatMessage(0, "Backup status - <col=00FF00>Yes</col>.");
    }
}