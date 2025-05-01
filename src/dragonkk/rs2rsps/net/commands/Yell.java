/*

No Retards Fuck This Up.

 */


package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.util.Misc;

public class Yell implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (p.playerMuted && p.getRights() < 2) {
            p.getFrames().sendChatMessage(0, "You are muted and cannot talk.");
            return;
        }
        if (p.getRights() < 1 && !p.trusted && !p.extremeDonator && !p.isForumMod) {
            p.getFrames().sendChatMessage(0, "You must be staff, extreme donator, trusted or super extreme to use this command.");
            return;
        }
        if (Server.muteSystem == 1) {
            p.getFrames().sendChatMessage(0, "Yelling has been disabled for everyone at the moment.");
            return;
        }
        if (p.yellremoved) {
            p.getFrames().sendChatMessage(0, "Your yell has been disabled.");
            return;
        }
        if (p.yellTimer > 0) {
            p.getFrames().sendChatMessage(0, "You must wait " + p.yellTimer + " seconds to yell.");
            return;
        }
        Server.lastMute = p;
        String[] rights555 = {"Player", "<img=0>Moderator<img=0>", "<img=1>"};
        String name = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
        int len = args.length;
        String yelled = "";
        String seperator;
        String right555 = rights555[p.getRights()];
        if (p.getRights() < 1) {
            if (p.isDonator)
                right555 = "Donator";
            if (p.extremeDonator)
                right555 = "Extreme Donator";
            if (p.getRights() == 1)
                right555 = "<img=0>Moderator<img=0>";
        }
        for (int i = 1; i < len; i++) {
            if (args[i].contains(">") || args[i].contains("<")) {
                p.getFrames().sendChatMessage(0, "You cannot use those symbols.");
                return;
            } else {
                seperator = i == 1 ? "" : " ";
                yelled = yelled + seperator + args[i];
            }
        }
        for (Player d : World.getPlayers()) {
            if (d == null)
                continue;
            p.yellTimer = 5;
            if (p.isForumMod) {
                d.getFrames().sendChatMessage(0, "<col=E6E6E6><shad=000000>[<img=0>Forum Mod<img=0>] [" + name + "] : " + yelled);
            } /*else if (p.getUsername().equals("baby_crysis")) {
                d.getFrames().sendChatMessage(0, "<col=ffffff><shad=000000>[<img=0>Beast Mod<img=0>] [" + name + "] <col=000000><shad=ffffff>: " + yelled);
            } else if (p.getUsername().equals("insidious")) {
                d.getFrames().sendChatMessage(0, "<col=ff0000><shad=000000>[eXtreme dOnator] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("jambon")) {
                d.getFrames().sendChatMessage(0, "<col=000000><shad=FFFF00>[Da Baws] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("agspureiam")) {
                d.getFrames().sendChatMessage(0, "<col=0101DF><shad=DF0101>[The Beast] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("kore")) {
                d.getFrames().sendChatMessage(0, "<col=FF8000><shad=A901DB>[Rune Pker] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("co_pure_gs")) {
                d.getFrames().sendChatMessage(0, "<col=000000><shad=ff0000>[<img=1>Administrator<img=1>] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("air")) {
                d.getFrames().sendChatMessage(0, "<col=0101DF><shad=848484>[Legend] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("emily_says")) {
                d.getFrames().sendChatMessage(0, "<col=33ffff><shad=3333ff>[Rawr] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("yp")) {
                d.getFrames().sendChatMessage(0, "<col=ffff00><shad=000000>[The Mighty] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("paixo")) {
                d.getFrames().sendChatMessage(0, "<col=00ffff><shad=0101df>[<img=0>Moderator<img=0>] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("angel")) {
                d.getFrames().sendChatMessage(0, "<col=F78181><shad=FE2E2E>[King] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("j4g3x")) {
                d.getFrames().sendChatMessage(0, "<col=080908><shad=049c16>[#1 Noob] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("the_dutchman")) {
                d.getFrames().sendChatMessage(0, "<col=0000ff><shad=f2f2f2>[-AFCA-] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("lmaooo")) {
                d.getFrames().sendChatMessage(0, "<col=000000><shad=ff0000>[#1 Oldfag] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("shot_down")) {
                d.getFrames().sendChatMessage(0, "<col=0404B4><shad=58D3F7>[Synn Owner] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("u_lag_i_rag")) {
                d.getFrames().sendChatMessage(0, "<col=000000><shad=f52887>[K.O.R] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("j_e_n_s")) {
                d.getFrames().sendChatMessage(0, "<col=ffffff><shad=000000>[Spank Me] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("b_l_0_w_m_3")) {
                d.getFrames().sendChatMessage(0, "<col=00CED1>[<img=0><col=FF0000>R</col><col=FFA500>a</col><col=FFFF00>i</col><col=008000>n</col><col=0000FF>b</col><col=4B0082>o</col><col=EE82EE>w </col><col=FF0000>P</col><col=FFFF00>o</col><col=0000FF>n</col><col=EE82EE>y</col><img=0><col=00CED1>] <col=ff0000>[" + name + "] :</col><col=00CED1> " + yelled);
            } else if (p.getUsername().equals("t_y_l_e_r")) {
                d.getFrames().sendChatMessage(0, "<col=000000><shad=FE9A2E>[Helper] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("sandyrip1")) {
                d.getFrames().sendChatMessage(0, "<col=40FF00><shad=04B404>[100% Legit] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("phil")) {
                d.getFrames().sendChatMessage(0, "<col=FA58F4><shad=000000>[God] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("chewy_1456")) {
                d.getFrames().sendChatMessage(0, "<col=000000><shad=40ff00>[<img=0>Moderator<img=0>] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("electricity")) {
                d.getFrames().sendChatMessage(0, "<col=00BFFF><shad=000000>[Yuh Done] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("bio_split")) {
                d.getFrames().sendChatMessage(0, "<col=BF00FF><shad=D8D8D8>[Pedo Bear] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("i_heart_kids")) {
                d.getFrames().sendChatMessage(0, "<col=8000FF><shad=01DF01>[1337 hearts] [" + name + "] : " + yelled);
            } else if (p.SolidGold) {
                d.getFrames().sendChatMessage(0, "<col=FACC2E><shad=FACC2E>[Solid Gold] [<shad=ffffff>" + name + "<shad=FACC2E>]<shad=000000> : " + yelled);
            } else if (p.getUsername().equals("wifey")) {
                d.getFrames().sendChatMessage(0, "<col=013ADF><shad=ff0000>[Spastic] <col=FF4000><shad=8A0868>[" + name + "] <col=8A0868><shad=00FFFF>: " + yelled);
            } else if (p.getUsername().equals("chris")) {
                d.getFrames().sendChatMessage(0, "<col=01DFD7><shad=000000>[SS REAPER] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("baby_spam")) {
                d.getFrames().sendChatMessage(0, "<col=04fafe><shad=000000>[Superman] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("tommy17890")) {
                d.getFrames().sendChatMessage(0, "<col=0040FF><shad=40FF00>[#1 Legit] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("death_kid")) {
                d.getFrames().sendChatMessage(0, "<col=120a8f><shad=90fefb>[<img=1>Administrator<img=1>] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("negro_please")) {
                d.getFrames().sendChatMessage(0, "<col=2EFEC8><shad=ffffff>[Jigaboo] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("infinitynoob")) {
                d.getFrames().sendChatMessage(0, "<col=00bee8><shad=000000>[Umadbro] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("th3_pkers")) {
                d.getFrames().sendChatMessage(0, "<col=0040FF><shad=01DF3A>[Super Extreme] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("ping")) {
                d.getFrames().sendChatMessage(0, "<col=008000><shad=000000>[Epic Pker] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("stilly")) {
                d.getFrames().sendChatMessage(0, "<col=ff0000><shad=000000>[Paper Hat] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("2_uf")) {
                d.getFrames().sendChatMessage(0, "<col=a9a9a9><shad=778899>[Less Than Three] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("conrad")) {
                d.getFrames().sendChatMessage(0, "<col=6600FF><shad=B97CFF>[<img=0>Moderator<img=0>] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("deathleus")) {
                d.getFrames().sendChatMessage(0, "<col=DF01D7><shad=FA58F4>[Lord of Hentai] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("poetry")) {
                d.getFrames().sendChatMessage(0, "<col=0000a0><shad=ff0000>[Poetic Tragedy] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("im_rude")) {
                d.getFrames().sendChatMessage(0, "<col=0040FF><shad=F7FE2E>[Camel Pride] [" + name + "] : " + yelled);
            } else if (p.getUsername().equals("jagex")) {
                d.getFrames().sendChatMessage(0, "<col=800080><shad=000000>[<img=3>Old Scammer] [" + name + "] : " + yelled);
            }*/ else if (p.getRights() == 1) {
                d.getFrames().sendChatMessage(0, "<col=E6E6E6><shad=000000>[<img=0>Moderator<img=0>] [" + name + "] : " + yelled);
            } else if (p.superextremeDonator && p.getRights() == 0) {
                d.getFrames().sendChatMessage(0, "<col=0040FF><shad=0404B4>[Super Extreme] [" + name + "] : " + yelled);
            } else if (p.trusted && !p.extremeDonator && p.getRights() == 0 && !p.superextremeDonator) {
                d.getFrames().sendChatMessage(0, "<col=ffffff><shad=FE2EC8>[Earned Trusted] [" + name + "] : " + yelled);
            } else if (p.trusted && p.extremeDonator && !p.superextremeDonator && p.getRights() == 0) {
                d.getFrames().sendChatMessage(0, "<col=ffffff><shad=800080>[Extreme Trusted] [" + name + "] : " + yelled);
            } else
                d.getFrames().sendChatMessage(0, "<col=FFFFFF><shad=04B404>[" + right555 + "] [" + name + "] : " + yelled);
        }
    }
}