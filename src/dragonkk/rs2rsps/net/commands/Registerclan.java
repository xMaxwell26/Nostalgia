package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Registerclan implements Command {


    public void execute(String[] args, final Player p) {
            /*if(!p.getCombat().isSafe(p)) {
				p.getFrames().sendChatMessage(0, "You can't use this command here.");
				return;
			}
		if(p.getSkills().playerDead)
			return;
        String owner = p.getUsername();
        if(World.getClanManager().getRegisteredClans().containsKey(args[1])) {
            p.getFrames().sendChatMessage(0,"That clan already exists.");
            return;
        }
        p.getClan = args[1];
       // World.getClanManager().getRegisteredClans().put(owner.toLowerCase(), World.getClanManager().clanChatsFile);
		Serializer.saveBanned(World.getClanManager().clanChatsFile, World.getClanManager().getRegisteredClans());
		Logger.log(this, "Registered new clan chat: " + owner);
        p.getFrames().sendChatMessage(0, "You created the clan ["+World.getClanManager().clan+"]."); */
        p.getFrames().sendChatMessage(0, "Command disabled, please create the clan through clan setup.");
    }
}
