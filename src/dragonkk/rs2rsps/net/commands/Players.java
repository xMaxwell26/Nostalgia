package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.util.Misc;

public class Players implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        if (World.getPlayers().size() > 299) { // Interface doesn't support 300+ lines and will crash.
            p.getFrames().sendChatMessage(0, "There are currently " + World.getPlayers().size() + " players online!");
            p.getFrames().sendChatMessage(0, "There are too many players on to fit on the list.");
            return;
        }
        int number = 0;
        for (int i = 0; i < 316; i++) {
            p.getFrames().sendString("", 275, i);
        }
        for (Player p5 : World.getPlayers()) {
            if (p5 == null)
                continue;
            number++;
            String titles = "";
            String[] rights555 = {"Player", "<img=0>Moderator<img=0>", "<img=0>Head Mod<img=0>", "<img=1>Forum Admin<img=1>"};
            String right = rights555[p.getRights()];
            String name = Misc.formatPlayerNameForDisplay(p5.getUsername().replaceAll("_", " "));
            if (p5.getRights() == 0) {
                titles = "";
            }
            if (p5.isDonator && !p5.extremeDonator) {
                titles = "<col=ff0000>[Donator] ";
            }
            if (p5.isDonator && p5.extremeDonator) {
                titles = "<col=FFFFFF><shad=4CC417>[Extreme] ";
            }
            if (p5.isDonator && p5.extremeDonator && p5.superextremeDonator) {
                titles = "<col=0040FF><shad=0404B4>[Super] ";
            }
            if (p5.getRights() == 1) {
                titles = "<col=BDBDBD><shad=BDBDBD>[Moderator] <img=0>";
            }
            if (p5.getRights() == 2) {
                titles = "<shad=cc0ff><col=9900CC>[Admin/Owner] <img=1>";
            }
            p.getFrames().sendString("" + titles + "" + name + "</col></shad> <col=33ff00><shad=33ff00>Kills: <col=3300ff>[" + p5.Kills2 + "]<col=33ff00> Level: <col=ff0000>[" + p5.getSkills().getCombatLevel() + "]</col></shad> <col=000000><shad=000000>Points: <shad=ff0000>[" + p5.Points + "]", 275, (16 + number));
        }
        p.getFrames().sendString("<u=000080>Players</u>", 275, 14);
        p.getFrames().sendString("Players Online: " + number, 275, 16);
        p.getFrames().sendString("Player's Online", 275, 2);
        p.getMask().setLastChatMessage(new ChatMessage(0, 0, "There are currently " + World.getPlayers().size() + " players online!"));
        p.getMask().setChatUpdate(true);
        p.getFrames().sendChatMessage(0, "There are currently " + World.getPlayers().size() + " players online!");
        //p.getFrames().sendChatMessage(0, "This command is temp disabled.");
        p.getFrames().sendInterface(275);
    }

}
